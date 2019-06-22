package com.github.bikeholik.drones.dispatcher.api;

import com.github.bikeholik.drones.dispatcher.data.Move;
import com.github.bikeholik.drones.dispatcher.data.MoveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class DispatcherController {

    private final MoveRepository moveRepository;

    @GetMapping("/v1/drones/{dronId}/moves")
    List<Move> getMoves(@PathVariable Long dronId,
                        @RequestParam(value = "lastSeenId", required = false, defaultValue = "0") Long lastSeenId,
                        @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults) {
        return moveRepository.getMoves(dronId, lastSeenId, maxResults, 350);
    }
}
