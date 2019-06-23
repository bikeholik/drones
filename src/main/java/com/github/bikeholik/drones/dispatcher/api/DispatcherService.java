package com.github.bikeholik.drones.dispatcher.api;

import com.github.bikeholik.drones.common.DroneProperties;
import com.github.bikeholik.drones.common.Move;
import com.github.bikeholik.drones.dispatcher.data.MoveRepository;
import com.github.bikeholik.drones.dispatcher.simulation.MovesDispatchedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DispatcherService {
    private final MoveRepository moveRepository;
    private final DroneProperties droneProperties;
    private final ApplicationEventPublisher applicationEventPublisher;

    List<Move> getMoves(Long droneId, Long lastMoveId, Integer maxResults) {
        List<Move> moves = moveRepository.getMoves(droneId, lastMoveId, maxResults, droneProperties.getMaxScanRangesInMeters());
        applicationEventPublisher.publishEvent(new MovesDispatchedEvent(droneId, moves.size()));
        return moves;
    }

}
