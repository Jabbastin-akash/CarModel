package com.example.CarModel;

public class BrandContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    // set brand (e.g. "bmw")
    public static void setBrand(String brand) {
        CONTEXT.set(brand);
    }

    // get current brand
    public static String getBrand() {
        return CONTEXT.get();
    }

    // clear after request
    public static void clear() {
        CONTEXT.remove();
    }
}

