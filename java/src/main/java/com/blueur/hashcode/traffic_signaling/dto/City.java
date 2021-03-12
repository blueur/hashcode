package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.List;
import io.vavr.collection.SortedMap;
import lombok.Data;

@Data
public class City {
    private int duration;
    private int intersectionsCount;
    private int streetsCount;
    private int carsCount;
    private int score;
    private SortedMap<String, Street> streets;
    private List<Intersection> intersections;
    private List<Car> cars;
}
