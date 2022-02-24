package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.Map;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Contributor {
    String id;
    Integer skillsCount;
    Map<String, Integer> skills;
}
