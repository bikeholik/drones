package com.github.bikeholik.drones.dispatcher.data;

import com.github.bikeholik.drones.common.Move;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoveRepository {

    @Query(nativeQuery = true,
            value = "select dl.*, group_concat(s.name) as stationNames from " +
                    "(select * from drone_location where drone_id = :droneId and id > :from order by id asc limit :maxSize) dl left join station s " +
                    "on ST_Distance_Sphere(Point(dl.latitude, dl.longitude), Point(s.latitude, s.longitude)) < :maxRange " +
                    "group by dl.latitude, dl.longitude, dl.id")
    List<Move> getMoves(@Param("droneId") Long droneId, @Param("from") Long from, @Param("maxSize") Integer maxSize, @Param("maxRange") Integer maxRange);
}
