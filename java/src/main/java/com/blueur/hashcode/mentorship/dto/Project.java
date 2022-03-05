package com.blueur.hashcode.mentorship.dto;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    private String id;
    private Integer day;
    private Integer score;
    private Integer bestBefore;
    private Integer skillsCount;
    private List<Tuple2<String, Integer>> skills;
}
