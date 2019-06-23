package com.github.bikeholik.drones.dispatcher.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
class Station implements Persistable<String> {

    @Id
    private String name;
    private double latitude;
    private double longitude;

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
