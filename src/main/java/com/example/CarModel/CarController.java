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
    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }
    @GetMapping("/{id}")
    public Car getCar(@PathVariable String id){
        Optional<Car> car = carRepository.findById(Long.parseLong(id));
        return car.orElse(null);
    }
    @PostMapping("/add")
    public Car addCar(@RequestBody Car car){
        return carRepository.save(car);

    }




}
