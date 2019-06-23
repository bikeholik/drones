package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.DroneProperties;
import com.github.bikeholik.drones.common.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
class DronesManager implements SmartLifecycle {
    private final DroneRepository droneRepository;
    private final DroneProperties droneProperties;
    private final DroneService droneService;
    private ExecutorService executorService;
    private volatile boolean running;

    @Override
    public void start() {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("drone-") {
            @Override
            public Thread createThread(Runnable runnable) {
                Thread thread = super.createThread(runnable);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.error("Error in {}", thread, e);
                    }
                });
                return thread;
            }
        };

        List<Long> droneIds = droneRepository.findDistinctDroneIds();
        executorService = Executors.newFixedThreadPool(droneIds.size(), threadFactory);
        droneIds.stream()
                .peek(id -> log.info("Starting drone with id {}", id))
                .map(this::createDrone)
                .forEach(executorService::execute);
        running = true;
    }

    private Runnable createDrone(Long id) {
        Random random = ThreadLocalRandom.current();
        return DroneTask.builder()
                .droneTaskProperties(DroneTaskProperties.of(
                        id,
                        droneProperties.getSpeedInMetersPerSecond() + random.nextInt(5),
                        droneProperties.getMemorySize() - random.nextInt(3)))
                .droneService(droneService)
                .build();
    }

    @Override
    @SneakyThrows
    public void stop() {
        log.info("Stopping drones...");
        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

}
