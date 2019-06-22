package com.github.bikeholik.drones.dispatcher.data;

import org.springframework.data.jpa.repository.JpaRepository;

interface DronLocationRepository extends JpaRepository<DronLocation, Long>, MoveRepository {
}
