package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.Move;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Builder
@Slf4j
class DroneTask implements Runnable {

    private final DroneTaskProperties droneTaskProperties;
    private final DroneService droneService;

    @Override
    public void run() {
        log.info("[{}] Started with memory {} and speed {}", droneTaskProperties.getId(), droneTaskProperties.getMemorySize(), droneTaskProperties.getSpeed());
        Move lastMove = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                lastMove = droneService.fetchNextMovesAndScan(lastMove, droneTaskProperties, this::simulateFlight)
                        .or(() -> {
                            log.debug("[{}] Wait as there was no reply", droneTaskProperties.getId());
                            simulateFlight(Duration.ofSeconds(5L));
                            return Optional.empty();
                        })
                        .orElse(lastMove);
            } catch (Exception e) {
                if (Objects.nonNull(e.getCause()) && e.getCause() instanceof InterruptedException) {
                    break;
                } else {
                    log.error("[{}] Unexpected error occurred", droneTaskProperties.getId(), e);
                }
            }
        }
        log.debug("[{}] Exiting...", droneTaskProperties.getId());
    }

    private void simulateFlight(Duration duration) {
        log.debug("[{}] Sleeping for {}", droneTaskProperties.getId(), duration);
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
