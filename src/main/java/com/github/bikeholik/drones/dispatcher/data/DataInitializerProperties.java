package com.github.bikeholik.drones.dispatcher.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "data.init")
@Configuration
@Data
class DataInitializerProperties {
    private String dataDirPath;
    private String stationsFileName;
    private boolean alwaysLoadLocationsEnabled;
}
