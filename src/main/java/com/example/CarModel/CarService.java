package com.example.CarModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService {

    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Car> getCarsByBrand(String brand) {
        BrandContextHolder.setBrand(brand); // choose DB
        try {
            return repo.findAll();
        } finally {
            BrandContextHolder.clear();
        }
    }
}
