package com.github.bikeholik.drones.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class TrafficInfo {
    private final Long droneId;
    private final OffsetDateTime timestamp;
    private final double droneSpeed;
    private final TrafficConditions trafficConditions;
    private final List<String> stationsNearby;

    public enum TrafficConditions {
        HEAVY, LIGHT, MODERATE
    }
}
