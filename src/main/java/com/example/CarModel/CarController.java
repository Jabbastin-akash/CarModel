package com.example.CarModel;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*") // Allow requests from any origin
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Save car into correct DB
    @PostMapping("/{brand}")
    public ResponseEntity<?> addCar(@PathVariable String brand, @RequestBody Car car) {
        logger.info("Received POST request for brand path: {} with car data: {}", brand, car);
        try {
            if (car == null) {
                return ResponseEntity.badRequest().body("Car cannot be null");
            }

            String dbKey = brand == null ? null : brand.toLowerCase();
            if (dbKey == null || !dbKey.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand path. Must be BMW, Audi, or Toyota");
            }

            if (car.getType() == null || car.getType().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Car type is required");
            }
            if (car.getImageurl() == null || car.getImageurl().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Car image URL is required");
            }
            if (car.getYear() <= 0) {
                return ResponseEntity.badRequest().body("Car year must be positive");
            }

            // Route to the target DB using the path variable, but DO NOT overwrite the car.brand from the request
            BrandContextHolder.setBrand(dbKey);
            try {
                Car saved = carService.save(car);
                return ResponseEntity.ok(saved);
            } finally {
                BrandContextHolder.clearBrand();
            }
        } catch (Exception e) {
            logger.error("Error saving car", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving car: " + e.getMessage());
        }
    }

    // Fetch all cars from correct DB
    @GetMapping("/{brand}/all")
    public ResponseEntity<?> getCars(@PathVariable String brand) {
        try {
            brand = brand.toLowerCase();
            if (!brand.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand. Must be BMW, Audi, or Toyota");
            }
            BrandContextHolder.setBrand(brand);
            try {
                List<Car> cars = carService.findAll();
                return ResponseEntity.ok(cars);
            } finally {
                BrandContextHolder.clearBrand();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching cars: " + e.getMessage());
        }
    }

    // Delete a car by ID from specified brand
    @DeleteMapping("/{brand}/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String brand, @PathVariable Long id) {
        try {
            brand = brand.toLowerCase();
            if (!brand.matches("^(bmw|audi|toyota)$")) {
                return ResponseEntity.badRequest().body("Invalid brand. Must be BMW, Audi, or Toyota");
            }
            BrandContextHolder.setBrand(brand);
            try {
                if (!carService.existsById(id)) {
                    return ResponseEntity.notFound().build();
                }
                carService.deleteById(id);
                return ResponseEntity.ok().body("Car with ID " + id + " deleted successfully from " + brand);
            } finally {
                BrandContextHolder.clearBrand();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting car: " + e.getMessage());
        }
    }

    // Brand specific endpoints
    @GetMapping(value = "/bmw", produces = "application/json")
    public ResponseEntity<?> getBmwCars() {
        try {
            BrandContextHolder.setBrand("bmw");
            try {
                return ResponseEntity.ok(carService.findAll());
            } finally { BrandContextHolder.clearBrand(); }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching BMW cars: " + e.getMessage());
        }
    }

    @GetMapping(value = "/audi", produces = "application/json")
    public ResponseEntity<?> getAudiCars() {
        try {
            BrandContextHolder.setBrand("audi");
            try {
                return ResponseEntity.ok(carService.findAll());
            } finally { BrandContextHolder.clearBrand(); }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching Audi cars: " + e.getMessage());
        }
    }

    @GetMapping(value = "/toyota", produces = "application/json")
    public ResponseEntity<?> getToyotaCars() {
        try {
            BrandContextHolder.setBrand("toyota");
            try {
                return ResponseEntity.ok(carService.findAll());
            } finally { BrandContextHolder.clearBrand(); }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching Toyota cars: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}
