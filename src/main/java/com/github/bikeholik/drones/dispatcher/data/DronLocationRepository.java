package com.github.bikeholik.drones.dispatcher.data;

import com.github.bikeholik.drones.common.DronRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface DronLocationRepository extends JpaRepository<DronLocation, Long>, MoveRepository, DronRepository {
}
