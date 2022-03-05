package com.blueur.hashcode.mentorship.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.mentorship.dto.*;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ParallelLearningSolver extends Solver<Team, Schedule> {
    @Override
    public Schedule solve(Team input) {
        java.util.List<Assignment> assignmentList = new ArrayList<>();
        Map<String, Map<Integer, java.util.List<Contributor>>> contributorsBySkill = input.getContributors().toStream()
                .flatMap(contributor -> contributor.getSkills().keySet().toStream()
                        .map(skillName -> new Tuple3<>(contributor, skillName, contributor.getSkills().get(skillName).get())))
                .collect(Collectors.groupingBy(tuple -> tuple._2, Collectors.groupingBy(tuple -> tuple._3, Collectors.mapping(tuple -> tuple._1, Collectors.toList()))));
        /* skill -> niveau -> contributors */

        java.util.Set<String> plannedContributors = new java.util.HashSet<>();
        java.util.Set<String> plannedProjects = new java.util.HashSet<>();

        do {
            plannedContributors.clear();
            for (Project project : input.getProjects().filter(project -> !plannedProjects.contains(project.getId()))) {
                java.util.List<Contributor> contributors = new ArrayList<>();
                for (Tuple2<String, Integer> skillWithLevel : project.getSkills()) {
                    Integer minSkillLevel = skillWithLevel._2;
                    Stream.range(minSkillLevel, 11)
                            .flatMap(skillLevel -> contributorsBySkill.get(skillWithLevel._1).computeIfAbsent(skillLevel, s -> java.util.List.of()))
                            .filter(contributor -> !plannedContributors.contains(contributor.getName()))
                            .filter(contributor -> contributors.stream().noneMatch(existingContributor -> existingContributor.getName().equals(contributor.getName())))
                            .headOption().forEach(contributors::add);
                }

                if (contributors.size() == project.getSkillsCount()) {
                    List<Contributor> vavrContributors = List.ofAll(contributors);
                    assignmentList.add(
                            Assignment
                                    .builder()
                                    .id(project.getId())
                                    .contributors(vavrContributors.map(Contributor::getName))
                                    .build()
                    );
                    vavrContributors.zip(project.getSkills())
                            .forEach(contributorWithSkill -> {
                                io.vavr.collection.Map<String, Integer> skillsOfContributor = contributorWithSkill._1.getSkills();
                                String skill = contributorWithSkill._2._1;
                                Integer requiredSkillLevel = contributorWithSkill._2._2;
                                if (requiredSkillLevel >= skillsOfContributor.get(skill).get()) {
                                    int newSkillLevel = skillsOfContributor.get(skill).get() + 1;
                                    contributorWithSkill._1.setSkills(skillsOfContributor.put(skill, newSkillLevel));
                                }
                            });
                    plannedProjects.add(project.getId());
                    plannedContributors.addAll(contributors.stream().map(Contributor::getName).collect(Collectors.toList()));
                }
            }
        } while (!plannedContributors.isEmpty());


        return Schedule.builder().assignments(List.ofAll(assignmentList)).build();
    }
}
