package com.example.CarModel;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.CarModel.BrandContextHolder.clear;

@Service
public class CarService {
    public final CarRepository repository;
    public CarService(CarRepository repository) {
        this.repository = repository;
    }
    @Transactional(readOnly = true)
    public List<Car> getCarsbyBrand(String brand){
        try{
            return repository.findAll();
        }
        finally{
            BrandContextHolder.clear();
        }

    }
    @Transactional
    public Car addCar(String Brand , Car car){
        BrandContextHolder.setBrand(Brand);
        try{
            return repository.save(car);

        }
        finally{
            BrandContextHolder.clear();
        }
    }



}
