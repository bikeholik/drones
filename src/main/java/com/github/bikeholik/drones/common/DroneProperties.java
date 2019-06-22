package com.github.bikeholik.drones.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "drone")
@Data
public class DroneProperties {
    private int maxScanRangesInMeters = 350;
    private double speedInMetersPerSecond = 10;
}
