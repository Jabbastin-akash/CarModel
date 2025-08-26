package com.example.CarModel;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class BrandRoutingDataSource extends AbstractRoutingDataSource {
    public static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
    public static void setBrand(String brand){
        CONTEXT.set(brand);
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return CONTEXT.get();
    }


}
