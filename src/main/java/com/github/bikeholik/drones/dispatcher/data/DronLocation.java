package com.github.bikeholik.drones.dispatcher.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
class DronLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long dronId;
    private double latitude;
    private double longitude;

}


