package com.example.CarModel;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "audiDataSource")
    public DataSource audiDataSource() {
        return DataSourceBuilder.create().build();

    }
    @Bean(name = "bmwDataSource")
    public DataSource bmwDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "toyotaDataSource")
    public DataSource toyotaDataSource(){
        return DataSourceBuilder.create().build();
    }
}
