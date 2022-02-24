package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Schedule {
    private int assignmentsCount;
    private List<Assignment> assignments;
}
