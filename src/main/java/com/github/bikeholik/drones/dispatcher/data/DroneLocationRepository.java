package com.github.bikeholik.drones.dispatcher.data;

import com.github.bikeholik.drones.common.DroneRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface DroneLocationRepository extends JpaRepository<DroneLocation, Long>, MoveRepository, DroneRepository {
}
