package com.github.bikeholik.drones.dispatcher.simulation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
class MovesDispatchedEventListener implements ApplicationContextAware {
    private final AtomicInteger noMoreMovesStrikeCounter = new AtomicInteger(-1);
    private ApplicationContext applicationContext;

    @EventListener
    @Async
    public void handle(MovesDispatchedEvent event) {
        MovesDispatchedEventDetails details = event.getDetails();
        log.info("{} moves sent to drone {}", details.getMovesCount(), details.getDroneId());

        if (anyMovesDispatched(details)) {
            noMoreMovesStrikeCounter.set(0);
        } else {
            if (shouldInterrupt()) {
                log.debug("Closing application...");
                if (applicationContext instanceof ConfigurableApplicationContext) {
                    ((ConfigurableApplicationContext) applicationContext).close();
                } else {
                    log.warn("Cannot close application of type {}", applicationContext.getClass());
                }
            }
        }
    }

    private boolean shouldInterrupt() {
        return noMoreMovesStrikeCounter.get() >= 0 && noMoreMovesStrikeCounter.incrementAndGet() > 5;
    }

    private boolean anyMovesDispatched(MovesDispatchedEventDetails event) {
        return event.getMovesCount() > 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
