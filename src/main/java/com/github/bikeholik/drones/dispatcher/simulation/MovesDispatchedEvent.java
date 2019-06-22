package com.github.bikeholik.drones.dispatcher.simulation;

import org.springframework.context.ApplicationEvent;

public class MovesDispatchedEvent extends ApplicationEvent {

    public MovesDispatchedEvent(Long droneId, int movesCount) {
        super(MovesDispatchedEventDetails.of(droneId, movesCount));
    }

    MovesDispatchedEventDetails getDetails(){
        return (MovesDispatchedEventDetails) getSource();
    }
}
