package com.github.bikeholik.drones;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class DronesApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DronesApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }

}
