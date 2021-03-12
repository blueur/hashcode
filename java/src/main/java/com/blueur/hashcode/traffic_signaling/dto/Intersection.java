package com.blueur.hashcode.traffic_signaling.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Intersection {
    private final int id;
    private Set<Street> incomingStreets = new HashSet<>();
    private Set<Street> outgoingStreets = new HashSet<>();
}
