package com.blueur.hashcode.mentorship.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.mentorship.dto.*;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ParallelNaiveSolver extends Solver<Team, Schedule> {
    @Override
    public Schedule solve(Team input) {
        java.util.List<Assignment> assignmentList = new ArrayList();
        Map<String, Map<Integer, java.util.List<Contributor>>> contributorsBySkill = input.getContributors().toStream()
                .flatMap(contributor -> contributor.getSkills().keySet().toStream()
                        .map(skillName -> new Tuple3<>(contributor, skillName, contributor.getSkills().get(skillName).get())))
                .collect(Collectors.groupingBy(tuple -> tuple._2, Collectors.groupingBy(tuple -> tuple._3, Collectors.mapping(tuple -> tuple._1, Collectors.toList()))));
        /* skill -> niveau -> contributors */

        java.util.Set<String> plannedContributors = new java.util.HashSet<>();
        java.util.Set<String> plannedProjects =  new java.util.HashSet<>();

        do {
            plannedContributors.clear();
            for (Project project : input.getProjects().filter(project -> !plannedProjects.contains(project.getId()))) {
                java.util.List<String> contributors = new ArrayList<>();
                for (Tuple2<String, Integer> skillWithLevel : project.getSkills()) {
                    Integer minSkillLevel = skillWithLevel._2;
                    Stream.range(minSkillLevel, 11)
                            .flatMap(skillLevel -> contributorsBySkill.get(skillWithLevel._1).computeIfAbsent(skillLevel, s -> java.util.List.of()))
                            .filter(contributor -> !plannedContributors.contains(contributor.getId()))
                            .filter(contributor -> !contributors.contains(contributor.getId()))
                            .map(Contributor::getId)
                            .headOption().forEach(contributors::add);
                }

                if (contributors.size() == project.getRolesCount()) {
                    assignmentList.add(
                            Assignment
                                    .builder()
                                    .id(project.getId())
                                    .contributors(io.vavr.collection.List.ofAll(contributors))
                                    .build()
                    );
                    plannedProjects.add(project.getId());
                    plannedContributors.addAll(contributors);
                }
            }
        } while (!plannedContributors.isEmpty());




        return Schedule.builder().assignments(io.vavr.collection.List.ofAll(assignmentList)).build();
    }
}
