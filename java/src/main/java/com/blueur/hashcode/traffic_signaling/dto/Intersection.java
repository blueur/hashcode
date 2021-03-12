package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Intersection {
    private final int id;
    private Set<Street> incomingStreets = HashSet.empty();
    private Set<Street> outgoingStreets = HashSet.empty();
}
