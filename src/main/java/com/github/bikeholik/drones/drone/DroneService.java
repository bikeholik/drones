package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.Move;
import com.github.bikeholik.drones.common.TrafficInfo;
import com.github.bikeholik.drones.drone.reporter.TrafficReporter;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
class DroneService {
    private final MovesProvider movesProvider;
    private final TrafficReporter trafficReporter;

    Optional<Move> fetchNextMovesAndScan(Move lastMove, DroneTaskProperties droneTaskProperties, Consumer<Duration> flightTimeHandler) {
        AtomicReference<Move> previousMove = new AtomicReference<>(lastMove);
        movesProvider.getMoves(droneTaskProperties.getId(), Optional.ofNullable(previousMove.get()).map(Move::getId).orElse(null), droneTaskProperties.getMemorySize())
                .stream()
                .peek(move -> log.info("[{}] Flying to [{}, {}]", droneTaskProperties.getId(), move.getLatitude(), move.getLongitude()))
                .peek(move -> Optional.ofNullable(previousMove.get())
                        .ifPresent(from -> calculateDistanceAndFly(from, move, droneTaskProperties, flightTimeHandler)))
                .peek(move -> scanTrafficIfNecessary(move, droneTaskProperties))
                .forEach(previousMove::set);
        return Optional.ofNullable(previousMove.get());
    }

    private void scanTrafficIfNecessary(Move move, DroneTaskProperties droneTaskProperties) {
        Optional.ofNullable(move.getStationNames())
                .filter(stations -> !stations.isEmpty())
                .map(stations -> TrafficInfo.builder()
                        .droneId(droneTaskProperties.getId())
                        .droneSpeed(droneTaskProperties.getSpeed())
                        .stationsNearby(stations)
                        .trafficConditions(getTrafficConditions())
                        .build())
                .ifPresent(trafficReporter::report);
    }

    private TrafficInfo.TrafficConditions getTrafficConditions() {
        return TrafficInfo.TrafficConditions.values()[ThreadLocalRandom.current().nextInt(TrafficInfo.TrafficConditions.values().length)];
    }

    private void calculateDistanceAndFly(Move from, Move to, DroneTaskProperties droneTaskProperties, Consumer<Duration> flightTimeHandler) {

        double distanceInMeters = EarthCalc.gcdDistance(
                Point.at(Coordinate.fromDegrees(to.getLatitude()), Coordinate.fromDegrees(to.getLongitude())),
                Point.at(Coordinate.fromDegrees(from.getLatitude()), Coordinate.fromDegrees(from.getLongitude())));

        long flightTime = (long) ((distanceInMeters / droneTaskProperties.getSpeed()) * 1000);

        flightTimeHandler.accept(Duration.ofMillis(flightTime));
    }
}
