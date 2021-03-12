package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Car {
    private final int id;
    private int pathsCount;
    private List<Street> paths;
}
