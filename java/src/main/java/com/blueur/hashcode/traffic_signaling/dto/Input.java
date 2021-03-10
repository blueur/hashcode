package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.List;
import lombok.Data;

@Data
public class Input {
    private int duration;
    private int intersectionsCount;
    private int streetsCount;
    private int carsCount;
    private int score;
    private List<Street> streets;
    private List<Intersection> intersections;
    private List<Car> cars;
}
