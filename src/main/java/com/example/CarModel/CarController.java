package com.example.CarModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // ✅ Brand-aware GET all cars
    @GetMapping("/{brand}/all")
    public List<Car> getAllCars(@PathVariable String brand) {
        try {
            BrandContextHolder.setBrand(brand.toLowerCase());
            return carRepository.findAll();
        } finally {
            BrandContextHolder.clear(); // avoid leaks
        }
    }

    // ✅ Brand-aware GET car by ID
    @GetMapping("/{brand}/{id}")
    public Optional<Car> getCarById(@PathVariable String brand, @PathVariable Long id) {
        try {
            BrandContextHolder.setBrand(brand.toLowerCase());
            return carRepository.findById(id);
        } finally {
            BrandContextHolder.clear();
        }
    }

    // ✅ Brand-aware CREATE car
    @PostMapping("/{brand}")
    public Car createCar(@PathVariable String brand, @RequestBody Car car) {
        try {
            BrandContextHolder.setBrand(brand.toLowerCase());
            return carRepository.save(car);
        } finally {
            BrandContextHolder.clear();
        }
    }

    // ✅ Brand-aware UPDATE car
    @PutMapping("/{brand}/{id}")
    public Car updateCar(@PathVariable String brand, @PathVariable Long id, @RequestBody Car carDetails) {
        try {
            BrandContextHolder.setBrand(brand.toLowerCase());
            return carRepository.findById(id).map(car -> {
                car.setBrand(carDetails.getBrand());
                car.setYear(carDetails.getYear());
                car.setType(carDetails.getType());
                car.setImageurl(carDetails.getImageurl());
                return carRepository.save(car);
            }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
        } finally {
            BrandContextHolder.clear();
        }
    }

    // ✅ Brand-aware DELETE car
    @DeleteMapping("/{brand}/{id}")
    public String deleteCar(@PathVariable String brand, @PathVariable Long id) {
        try {
            BrandContextHolder.setBrand(brand.toLowerCase());
            carRepository.deleteById(id);
            return "Car with ID " + id + " deleted from " + brand + "!";
        } finally {
            BrandContextHolder.clear();
        }
    }
}
