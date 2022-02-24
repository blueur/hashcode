package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.Map;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Project {
    final String id;
    final Integer day;
    final Integer score;
    final Integer bestBefore;
    final Integer rolesCount;
    final Map<String, Integer> skills;
}
