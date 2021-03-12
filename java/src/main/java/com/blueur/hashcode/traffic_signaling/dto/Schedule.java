package com.blueur.hashcode.traffic_signaling.dto;

import io.vavr.collection.LinkedHashMap;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {
    Intersection intersection;
    LinkedHashMap<String, Integer> lightDurations;
}
