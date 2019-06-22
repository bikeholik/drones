package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.drone.reporter.TrafficReporter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
class Drone implements Runnable {

    private final DroneProperties droneProperties;
    private final MovesProvider movesProvider;
    private final TrafficReporter trafficReporter;

    @Override
    public void run() {
        log.info("[{}] Started with memory {} and speed {}", droneProperties.id, droneProperties.memorySize, droneProperties.speed);
        while (!Thread.currentThread().isInterrupted()) {
            log.info("[{}] flying...", droneProperties.id);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.info("Interrupted");
                return;
            }
        }
    }

    @RequiredArgsConstructor(staticName = "of")
    static class DroneProperties {
        private final Long id;
        private final double speed;
        private final int memorySize;
    }

}
