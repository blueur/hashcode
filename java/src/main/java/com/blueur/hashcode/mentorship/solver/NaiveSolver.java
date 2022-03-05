package com.blueur.hashcode.mentorship.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.mentorship.dto.*;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class NaiveSolver extends Solver<Team, Schedule> {
    @Override
    public Schedule solve(Team input) {
        List<Assignment> assignmentList = new ArrayList();
        Map<String, Map<Integer, List<Contributor>>> contributorsBySkill = input.getContributors().toStream()
                .flatMap(contributor -> contributor.getSkills().keySet().toStream()
                        .map(skillName -> new Tuple3<>(contributor, skillName, contributor.getSkills().get(skillName).get())))
                .collect(Collectors.groupingBy(tuple -> tuple._2, Collectors.groupingBy(tuple -> tuple._3, Collectors.mapping(tuple -> tuple._1, Collectors.toList()))));
        /* skill -> niveau -> contributors */

        for (Project project : input.getProjects()) {
            List<String> contributors = new ArrayList<>();
            for (Tuple2<String, Integer> skillWithLevel : project.getSkills()) {
                Integer minSkillLevel = skillWithLevel._2;
                Stream.range(minSkillLevel, 11)
                        .flatMap(skillLevel -> contributorsBySkill.get(skillWithLevel._1).computeIfAbsent(skillLevel, s -> List.of()))
                        .filter(contributor -> !contributors.contains(contributor.getName()))
                        .map(Contributor::getName)
                        .headOption().forEach(contributors::add);
            }

            if (contributors.size() == project.getSkillsCount()) {
                assignmentList.add(
                        Assignment
                                .builder()
                                .id(project.getId())
                                .contributors(io.vavr.collection.List.ofAll(contributors))
                                .build()
                );
            }
        }

        return Schedule.builder().assignments(io.vavr.collection.List.ofAll(assignmentList)).build();
    }
}
