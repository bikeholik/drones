package com.github.bikeholik.drones.dispatcher.api;

import com.github.bikeholik.drones.common.Move;
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

    private final DispatcherService dispatcherService;

    @GetMapping("/v1/drones/{droneId}/moves")
    List<Move> getMoves(@PathVariable Long droneId,
                        @RequestParam(value = "lastSeenId", required = false, defaultValue = "0") Long lastSeenId,
                        @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults) {
        return dispatcherService.getMoves(droneId, lastSeenId, maxResults);
    }
}
