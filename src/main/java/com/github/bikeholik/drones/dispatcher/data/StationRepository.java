package com.github.bikeholik.drones.dispatcher.data;

import org.springframework.data.jpa.repository.JpaRepository;

interface StationRepository extends JpaRepository<Station, String> {
}
