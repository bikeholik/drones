package com.github.bikeholik.drones.drone.reporter;

import com.github.bikeholik.drones.common.TrafficInfo;

public interface TrafficReporter {
    void report(TrafficInfo trafficInfo);
}
