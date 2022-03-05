package com.blueur.hashcode.mentorship.dto;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Contributor {
    private String name;
    private Integer skillsCount;
    private List<Tuple2<String, Integer>> skillsList;
    private Map<String, Integer> skills;
}
