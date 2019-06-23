package com.github.bikeholik.drones.dispatcher.api;

import com.github.bikeholik.drones.common.Move;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.bikeholik.drones.common.Constants.PARAM_LAST_MOVE_ID;
import static com.github.bikeholik.drones.common.Constants.PARAM_MAX_RESULTS;

@RestController
@RequiredArgsConstructor
class DispatcherController {

    private final DispatcherService dispatcherService;

    @GetMapping("/v1/drones/{droneId}/moves")
    List<Move> getMoves(@PathVariable Long droneId,
                        @RequestParam(value = PARAM_LAST_MOVE_ID, required = false, defaultValue = "0") Long lastMoveId,
                        @RequestParam(value = PARAM_MAX_RESULTS, required = false, defaultValue = "10") Integer maxResults) {
        return dispatcherService.getMoves(droneId, lastMoveId, maxResults);
    }
}
