package com.github.bikeholik.drones.common;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DronRepository {
    @Query("select distinct dronId from DronLocation")
    List<Long> findDistinctDronIds();
}
