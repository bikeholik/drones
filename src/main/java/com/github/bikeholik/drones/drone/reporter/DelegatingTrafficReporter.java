package com.github.bikeholik.drones.drone.reporter;

import com.github.bikeholik.drones.common.TrafficInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
class DelegatingTrafficReporter implements TrafficReporter {
    private final List<TrafficReporter> trafficReporters;

    @Override
    public void report(TrafficInfo trafficInfo) {
        trafficReporters.forEach(reporter -> reporter.report(trafficInfo));
    }
}
