package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.mentorship.dto.Contributor;
import com.blueur.hashcode.mentorship.dto.Project;
import com.blueur.hashcode.mentorship.dto.Team;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

public class TeamParser extends Parser<Team> {

    public TeamParser(Path path) {
        super(path);
    }

    @Override
    public Team parseIterator(Iterator<String> fileIterator) {
        Integer[] firstLine = Arrays.stream(fileIterator.next().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        int contributorsCount = firstLine[0];
        int projectsCount = firstLine[1];
        List<Contributor> contributors = Stream.range(0, contributorsCount).map(i -> {
            String[] line = fileIterator.next().split(" ");
            String name = line[0];
            int skillsCount = Integer.parseInt(line[1]);
            Map<String, Integer> skills = LinkedHashMap.empty();
            for (int j = 0; j < skillsCount; j++) {
                String[] skillLine = fileIterator.next().split(" ");
                skills = skills.put(skillLine[0], Integer.parseInt(skillLine[1]));
            }
            return Contributor.builder()
                    .id(name)
                    .skillsCount(skillsCount)
                    .skills(skills)
                    .build();
        }).toList();

        List<Project> projects = Stream.range(0, projectsCount).map(i -> {
            String[] line = fileIterator.next().split(" ");
            String name = line[0];
            int day = Integer.parseInt(line[1]);
            int score = Integer.parseInt(line[2]);
            int bestBefore = Integer.parseInt(line[3]);
            int rolesCount = Integer.parseInt(line[4]);
            List<Tuple2<String, Integer>> roles = List.empty();
            for (int j = 0; j < rolesCount; j++) {
                String[] roleLine = fileIterator.next().split(" ");
                roles = roles.append(Tuple.of(roleLine[0], Integer.parseInt(roleLine[1])));
            }
            return Project.builder()
                    .id(name)
                    .day(day)
                    .score(score)
                    .bestBefore(bestBefore)
                    .rolesCount(rolesCount)
                    .skills(roles)
                    .build();
        }).toList();

        return Team.builder()
                .contributorsCount(contributorsCount)
                .projectsCount(projectsCount)
                .contributors(contributors)
                .projects(projects)
                .build();
    }
}
