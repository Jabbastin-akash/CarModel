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


    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    @GetMapping("/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return carRepository.findById(id);
    }


    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carRepository.save(car);
    }


    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setBrand(carDetails.getBrand());
            car.setYear(carDetails.getYear());
            car.setType(carDetails.getType());
            car.setImageurl(carDetails.getImageurl());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    // ✅ Delete a car
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
        return "Car with ID " + id + " deleted!";
    }
}
