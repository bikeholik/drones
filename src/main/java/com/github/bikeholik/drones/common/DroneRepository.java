package com.github.bikeholik.drones.common;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DroneRepository {
    @Query("select distinct droneId from DroneLocation")
    List<Long> findDistinctDroneIds();
}
