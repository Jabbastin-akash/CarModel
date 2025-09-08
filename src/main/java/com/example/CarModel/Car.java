package com.example.CarModel;

import jakarta.persistence.*;

@Entity
@Table(name = "car_db")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String brand;
    private int year;
    private String type;
    private String model;
    private String engine_type;
    private String fuel_efficiency;
    private String imageurl;
    private float price;
    private String transmission;
    private int SeatingCapacity;
    private String features;

    // Getters
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public String getFuel_efficiency() {
        return fuel_efficiency;
    }

    public String getImageurl() {
        return imageurl;
    }

    public float getPrice() {
        return price;
    }

    public String getTransmission() {
        return transmission;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public String getFeatures() {
        return features;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    public void setFuel_efficiency(String fuel_efficiency) {
        this.fuel_efficiency = fuel_efficiency;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.SeatingCapacity = seatingCapacity;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
