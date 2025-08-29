package com.example.CarModel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private DataSource createDataSource(String dbName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Jabbastin@Akash1");
        return dataSource;
    }

    @Bean(name = "toyotaDataSource")
    public DataSource toyotaDataSource() {
        return createDataSource("toyota");
    }

    @Bean(name = "bmwDataSource")
    public DataSource bmwDataSource() {
        return createDataSource("bmw");
    }

    @Bean(name = "audiDataSource")
    public DataSource audiDataSource() {
        return createDataSource("audi");
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(
            @Qualifier("bmwDataSource") DataSource bmwDataSource,
            @Qualifier("audiDataSource") DataSource audiDataSource,
            @Qualifier("toyotaDataSource") DataSource toyotaDataSource) {

        BrandRoutingDataSource routingDataSource = new BrandRoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("bmw", bmwDataSource);
        targetDataSources.put("audi", audiDataSource);
        targetDataSources.put("toyota", toyotaDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(bmwDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}
