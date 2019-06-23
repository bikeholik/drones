package com.github.bikeholik.drones.drone;

import com.github.bikeholik.drones.common.Move;
import com.github.bikeholik.drones.drone.reporter.TrafficReporter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DroneServiceTest {

    private final AtomicLong moveIndex = new AtomicLong(1);
    private final Random random = new Random();
    private final DroneTaskProperties properties = DroneTaskProperties.of(1L, 8.0, 2);

    @Mock
    private Consumer<Duration> flightTimeHandler;
    @Mock
    private MovesProvider movesProvider;
    @Mock
    private TrafficReporter trafficReporter;
    @InjectMocks
    private DroneService droneService;


    @Test
    public void fetchNextMovesAndScanNoStations() {
        when(movesProvider.getMoves(anyLong(), any(), anyInt()))
                .thenAnswer((Answer<List<Move>>) invocation -> Collections.singletonList(move()));

        Optional<Move> lastMove = droneService.fetchNextMovesAndScan(null, properties, flightTimeHandler);

        assertThat(lastMove).isPresent();
        verifyZeroInteractions(flightTimeHandler);
        verifyZeroInteractions(trafficReporter);
    }

    @Test
    public void fetchNextMovesAndScanWithFlight() {
        when(movesProvider.getMoves(anyLong(), anyLong(), anyInt()))
                .thenAnswer((Answer<List<Move>>) invocation -> Collections.singletonList(move()));

        Optional<Move> lastMove = droneService.fetchNextMovesAndScan(move(), properties, flightTimeHandler);

        assertThat(lastMove).isPresent();
        verify(flightTimeHandler).accept(any(Duration.class));
        verifyZeroInteractions(trafficReporter);
    }

    @Test
    public void fetchNextMovesAndScanWithReport() {
        when(movesProvider.getMoves(anyLong(), anyLong(), anyInt()))
                .thenAnswer((Answer<List<Move>>) invocation -> Collections.singletonList(move("test")));

        Optional<Move> lastMove = droneService.fetchNextMovesAndScan(move(), properties, flightTimeHandler);

        assertThat(lastMove).isPresent();
        verify(flightTimeHandler).accept(any(Duration.class));
        verify(trafficReporter).report(any());
    }

    private Move move(String... stations) {
        Move move = mock(Move.class);
        when(move.getId()).thenReturn(moveIndex.getAndIncrement());
        when(move.getLatitude()).thenReturn(random.nextDouble() * 90);
        when(move.getLongitude()).thenReturn(random.nextDouble() * 90);
        when(move.getStationNames()).thenReturn(Arrays.asList(stations));
        return move;
    }
}
