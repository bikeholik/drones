package com.github.bikeholik.drones.dispatcher.simulation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "of")
class MovesDispatchedEventDetails {
    private final Long droneId;
    private final int movesCount;
}
