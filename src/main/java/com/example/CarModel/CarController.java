package com.example.CarModel;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Save car into correct DB
    @PostMapping("/{brand}")
    public ResponseEntity<?> addCar(@PathVariable String brand, @RequestBody Car car) {
        try {
            if (car == null || brand == null) {
                return ResponseEntity.badRequest().body("Car and brand cannot be null");
            }

            // Validate brand name
            brand = brand.toLowerCase();
            if (!brand.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand. Must be BMW, Audi, or Toyota");
            }

            // Set the brand context and save
            BrandContextHolder.setBrand(brand);
            Car savedCar = carRepository.save(car);
            return ResponseEntity.ok(savedCar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving car: " + e.getMessage());
        }
    }

    // Fetch all cars from correct DB
    @GetMapping("/{brand}/all")
    public ResponseEntity<?> getCars(@PathVariable String brand) {
        try {
            // Validate brand name
            brand = brand.toLowerCase();
            if (!brand.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand. Must be BMW, Audi, or Toyota");
            }

            BrandContextHolder.setBrand(brand);
            List<Car> cars = carRepository.findAll();
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching cars: " + e.getMessage());
        }
    }

    // Delete a car by ID from specified brand
    @DeleteMapping("/{brand}/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String brand, @PathVariable Long id) {
        try {
            // Validate brand name
            brand = brand.toLowerCase();
            if (!brand.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand. Must be BMW, Audi, or Toyota");
            }

            BrandContextHolder.setBrand(brand);
            if (!carRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            carRepository.deleteById(id);
            return ResponseEntity.ok().body("Car with ID " + id + " deleted successfully from " + brand);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting car: " + e.getMessage());
        }
    }

    // Handle validation errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred: " + e.getMessage());
    }
}
