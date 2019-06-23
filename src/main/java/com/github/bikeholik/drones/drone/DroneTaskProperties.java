package com.github.bikeholik.drones.drone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
class DroneTaskProperties {
    private final Long id;
    private final double speed;
    private final int memorySize;
}
