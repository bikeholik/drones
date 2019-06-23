package com.github.bikeholik.drones.drone.reporter;

import com.github.bikeholik.drones.common.Constants;
import com.github.bikeholik.drones.common.TrafficInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("spring.kafka.bootstrap-servers")
@RequiredArgsConstructor
class KafkaTrafficReporter implements TrafficReporter {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    public void report(TrafficInfo trafficInfo) {
        kafkaTemplate.send(Constants.TOPIC_TRAFFIC_REPORTS, trafficInfo);
    }
}
