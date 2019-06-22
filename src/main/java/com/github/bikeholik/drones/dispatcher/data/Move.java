package com.github.bikeholik.drones.dispatcher.data;

import java.util.List;

public interface Move {
    double getLatitude();
    double getLongitude();
    Long getId();
    List<String> getStationNames();
}
