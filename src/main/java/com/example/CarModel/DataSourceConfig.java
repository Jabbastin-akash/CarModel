package com.example.CarModel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "toyotaDataSource")
    public DataSource toyotaDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/toyota")
                .username("root")
                .password("Jabbastin@Akash1")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean(name = "bmwDataSource")
    public DataSource bmwDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/bmw")
                .username("root")
                .password("Jabbastin@Akash1")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean(name = "audiDataSource")
    public DataSource audiDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/audi")
                .username("root")
                .password("Jabbastin@Akash1")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Primary
    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
            @Qualifier("bmwDataSource") DataSource bmwDataSource,
            @Qualifier("audiDataSource") DataSource audiDataSource,
            @Qualifier("toyotaDataSource") DataSource toyotaDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("bmw", bmwDataSource);
        targetDataSources.put("audi", audiDataSource);
        targetDataSources.put("toyota", toyotaDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(bmwDataSource);

        return routingDataSource;
    }
}
