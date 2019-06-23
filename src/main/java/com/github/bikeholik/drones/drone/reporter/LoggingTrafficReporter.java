package com.github.bikeholik.drones.drone.reporter;

import com.github.bikeholik.drones.common.TrafficInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class LoggingTrafficReporter implements TrafficReporter {
    @Override
    public void report(TrafficInfo trafficInfo) {
        log.info("{}", trafficInfo);
    }
}
