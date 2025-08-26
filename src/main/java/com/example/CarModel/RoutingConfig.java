package com.example.CarModel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RoutingConfig {
    @Bean
    public DataSource dataSource(
        @Qualifier("audiDataSource") DataSource audi,
        @Qualifier("bmwDataSource") DataSource bmw,
        @Qualifier("toyotaDataSource")DataSource toyota){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("audi",audi);
        targetDataSources.put("bmw",bmw);
        targetDataSources.put("toyota",toyota);
        BrandRoutingDataSource routingDataSource = new BrandRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(toyota);
        return routingDataSource;



    }

}
