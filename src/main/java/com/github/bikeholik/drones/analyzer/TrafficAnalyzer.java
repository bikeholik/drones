package com.github.bikeholik.drones.analyzer;

import com.github.bikeholik.drones.common.TrafficInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.github.bikeholik.drones.common.Constants.TOPIC_TRAFFIC_REPORTS;

@Component
@Slf4j
class TrafficAnalyzer {

    @KafkaListener(topics = TOPIC_TRAFFIC_REPORTS)
    void analyze(TrafficInfo trafficInfo) {
        log.info("[traffic-report] {} {} {}m/s; {} traffic in {}",
                trafficInfo.getTimestamp(),
                trafficInfo.getDroneId(),
                trafficInfo.getDroneSpeed(),
                trafficInfo.getTrafficConditions(),
                String.join(",", trafficInfo.getStationsNearby())
        );
    }
}
