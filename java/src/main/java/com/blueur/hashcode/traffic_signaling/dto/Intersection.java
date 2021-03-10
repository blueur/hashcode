package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Intersection {
    private final int id;
    private List<Street> incomingStreets = List.empty();
    private List<Street> outgoingStreets = List.empty();
}
