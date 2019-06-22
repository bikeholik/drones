package com.github.bikeholik.drones.dispatcher.data;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.trimLeadingCharacter;
import static org.springframework.util.StringUtils.trimTrailingCharacter;

@Component
@RequiredArgsConstructor
@Slf4j
class DataInitializer {
    private final DataInitializerProperties initializerProperties;
    private final StationRepository stationRepository;
    private final DroneLocationRepository droneLocationRepository;

    @PostConstruct
    @SneakyThrows
    public void init() {
        if (!initializerProperties.isAlwaysLoadLocationsEnabled()) {
            if (droneLocationRepository.count() > 0) {
                log.info("Data already loaded");
                return;
            }
        }
        Stream.ofNullable(initializerProperties.getDataDirPath())
                .flatMap(this::getDataFiles)
                .forEach(this::load);
    }

    @SneakyThrows
    private Stream<Path> getDataFiles(String path) {
        return Files.list(Path.of(path));
    }

    private void load(Path path) {
        if (Objects.equals(initializerProperties.getStationsFileName(), path.getFileName().toFile().getName())) {
            loadStations(path);
        } else {
            loadDroneLocations(path);
        }
    }

    private void loadDroneLocations(Path path) {
        List<DroneLocation> locations = parse(path, chunks -> chunks.length >= 3, chunks -> DroneLocation.builder()
                .droneId(Long.parseLong(chunks[0]))
                .latitude(parseCoordinate(1, chunks))
                .longitude(parseCoordinate(2, chunks))
                .build());
        droneLocationRepository.saveAll(locations);
    }

    private void loadStations(Path path) {
        List<Station> stations = parse(path, chunks -> chunks.length == 3, chunks -> Station.builder()
                .name(trim(chunks[0]))
                .latitude(parseCoordinate(1, chunks))
                .longitude(parseCoordinate(2, chunks))
                .build());
        stationRepository.saveAll(stations);
    }

    private String trim(String chunk) {
        return trimTrailingCharacter(trimLeadingCharacter(chunk, '"'), '"');
    }

    @SneakyThrows
    private <T> List<T> parse(Path path, Predicate<String[]> validator, Function<String[], T> mapper) {
        return Files.readAllLines(path).stream()
                .map(line -> line.split(","))
                .filter(validator)
                .map(mapper)
                .collect(Collectors.toList());
    }

    private double parseCoordinate(int index, String[] chunks) {
        return Double.parseDouble(trim(chunks[index]));
    }
}
