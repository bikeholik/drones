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
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private ApplicationContext applicationContext;

    @EventListener
    @Async
    public void handle(MovesDispatchedEvent event) {
        log.debug("{}", event.getSource());

        if (event.getDetails().getMovesCount() > 0) {
            atomicInteger.set(0);
        } else {
            if (atomicInteger.incrementAndGet() > 2) {
                log.debug("Closing application...");
                if (applicationContext instanceof ConfigurableApplicationContext) {
                    ((ConfigurableApplicationContext) applicationContext).close();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
