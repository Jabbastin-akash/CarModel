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
    public List<Car> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Car save(Car car) {
        return repo.save(car);
    }

    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
