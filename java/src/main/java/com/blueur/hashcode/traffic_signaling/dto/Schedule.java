package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.LinkedHashMap;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Schedule {
    Intersection intersection;
    LinkedHashMap<String, Integer> greenLightsDurations;
}
