package com.github.bikeholik.drones.common;

import java.util.List;

public interface Move {
    double getLatitude();
    double getLongitude();
    Long getId();
    List<String> getStationNames();
}
