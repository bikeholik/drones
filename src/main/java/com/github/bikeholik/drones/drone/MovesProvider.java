package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.Constants;
import com.github.bikeholik.drones.common.DroneProperties;
import com.github.bikeholik.drones.common.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
class MovesProvider {
    private final RestTemplate restTemplate = new RestTemplate();
    private final DroneProperties droneProperties;

    @Retryable(backoff = @Backoff(multiplier = 1.5))
    List<Move> getMoves(Long dronedId, Long lastMoveId, int memorySize) {
        return Optional.ofNullable(restTemplate.getForObject(
                droneProperties.getDispatcherBaseUrl() + "/v1/drones/{droneId}/moves?" +
                        Constants.PARAM_LAST_MOVE_ID +
                        "={lastMovedId}&" +
                        Constants.PARAM_MAX_RESULTS +
                        "={maxResults}",
                MoveWrapper[].class,
                dronedId, lastMoveId, memorySize))
                .map(moves -> (Move[]) moves)
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);
    }

    @Recover
    List<Move> noMoves(Throwable throwable) {
        log.warn("Error calling dispatcher", throwable);
        return Collections.emptyList();
    }

    @AllArgsConstructor
    @Getter
    private static class MoveWrapper implements Move {
        private final double latitude;
        private final double longitude;
        private final List<String> stationNames;
        private final Long id;

    }
}
