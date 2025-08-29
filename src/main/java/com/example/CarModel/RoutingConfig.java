package com.example.CarModel;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
    // Disabled: conflicting DataSource bean definition was causing ambiguity.
    // The application now uses the primary routing DataSource defined in DataSourceConfig.
}
