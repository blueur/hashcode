package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Team {
    private int contributorsCount;
    private int projectsCount;
    private List<Contributor> contributors;
    private List<Project> projects;
}

