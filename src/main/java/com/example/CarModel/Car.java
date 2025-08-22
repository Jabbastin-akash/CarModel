package com.example.CarModel;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;
    private int year;
    private String type;
    private String imageurl;

    // Default constructor (required by JPA)
    public Car() {}

    // Parameterized constructor
    public Car(Long id, String brand, int year, String type, String imageurl) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.imageurl = imageurl;
    }

    // Getters
    public Long getId() {
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

    public String getImageurl() {
        return imageurl;
    }

    // ✅ Setters (needed for update & POST request)
    public void setId(Long id) {
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

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
