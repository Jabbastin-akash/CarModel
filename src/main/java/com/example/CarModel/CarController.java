package com.example.CarModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FileStorageService fileStorageService;

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

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String filePath = fileStorageService.storeFile(file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", filePath);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-with-image")
    public Car addCarWithImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("brand") String brand,
            @RequestParam("year") int year,
            @RequestParam("type") String type,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "engine_type", required = false) String engineType,
            @RequestParam(value = "fuel_efficiency", required = false) String fuelEfficiency,
            @RequestParam(value = "price", required = false, defaultValue = "0") float price,
            @RequestParam(value = "transmission", required = false) String transmission,
            @RequestParam(value = "seatingCapacity", required = false, defaultValue = "0") int seatingCapacity,
            @RequestParam(value = "features", required = false) String features) {

        String imageUrl = fileStorageService.storeFile(file);

        Car car = new Car();
        car.setBrand(brand);
        car.setYear(year);
        car.setType(type);
        car.setModel(model);
        car.setEngine_type(engineType);
        car.setFuel_efficiency(fuelEfficiency);
        car.setImageurl(imageUrl);
        car.setPrice(price);
        car.setTransmission(transmission);
        car.setSeatingCapacity(seatingCapacity);
        car.setFeatures(features);

        return carRepository.save(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        try {
            carRepository.deleteById(Long.parseLong(id));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
