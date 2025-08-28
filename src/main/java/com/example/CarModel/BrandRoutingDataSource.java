package com.example.CarModel;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class BrandRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return BrandContextHolder.getBrand();
    }
}
