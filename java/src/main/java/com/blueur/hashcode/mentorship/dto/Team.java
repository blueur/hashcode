package com.blueur.hashcode.mentorship.dto;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Team {
    private int contributorsCount;
    private int projectsCount;
    private List<Contributor> contributors;
    private List<Project> projects;
}
