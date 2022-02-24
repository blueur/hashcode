package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {
    private List<Assignment> assignments;
}
