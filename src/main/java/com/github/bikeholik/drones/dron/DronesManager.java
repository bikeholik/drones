package com.github.bikeholik.drones.dron;

import com.github.bikeholik.drones.common.DronRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
class DronesManager implements SmartLifecycle {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10, new CustomizableThreadFactory("drone"));
    private final DronRepository dronRepository;
    private volatile boolean running;

    @Override
    public void start() {
        dronRepository.findDistinctDronIds().stream()
                .peek(id -> log.info("Starting dron {}", id))
                .map(this::createDron)
                .forEach(executorService::execute);
        running = true;
    }

    private Runnable createDron(Long id) {
        return new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    log.info("{} flying...", id);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.info("Interrupted");
                        return;
                    }
                }
            }
        };
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
