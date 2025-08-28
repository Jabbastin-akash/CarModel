package com.example.CarModel;

public class BrandContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setBrand(String brand) {
        CONTEXT.set(brand);
    }

    public static String getBrand() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
