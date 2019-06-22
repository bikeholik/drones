package com.github.bikeholik.drones.drone.reporter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LoggingTrafficReporter implements TrafficReporter {
    @Override
    public void report(Object o) {
        log.info("{}", o);
    }
}
