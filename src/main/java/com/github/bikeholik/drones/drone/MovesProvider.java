package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.Move;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
class MovesProvider {
    private final RestTemplate restTemplate = new RestTemplate();

    List<Move> getMoves(Long dronedId, Long lastMoveIdSeen, int memorySize){
        return null;
    }
}
