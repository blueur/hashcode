package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.mentorship.dto.Contributor;
import com.blueur.hashcode.mentorship.dto.Project;
import com.blueur.hashcode.mentorship.dto.Team;
import io.vavr.Tuple2;

import java.nio.file.Path;
import java.util.Iterator;

public class TeamParser extends Parser<Team> {

    public TeamParser(Path path) {
        super(path);
    }

    @Override
    public Team parseIterator(Iterator<String> fileIterator) {
        final Team team = object(Team::new,
                line(
                        integer(Team::setContributorsCount),
                        integer(Team::setProjectsCount)
                ),
                list(Team::getContributorsCount, Team::setContributors, object(Contributor::new,
                        line(string(Contributor::setName), integer(Contributor::setSkillsCount)),
                        list(Contributor::getSkillsCount, Contributor::setSkillsList, lineTuple2(STRING, INTEGER))
                )),
                list(Team::getProjectsCount, Team::setProjects, object(Project::new,
                        line(
                                string(Project::setId),
                                integer(Project::setDay),
                                integer(Project::setScore),
                                integer(Project::setBestBefore),
                                integer(Project::setSkillsCount)
                        ),
                        list(Project::getSkillsCount, Project::setSkills, lineTuple2(STRING, INTEGER))
                ))
        ).apply(fileIterator);
        return team.toBuilder()
                .contributors(team.getContributors().map(contributor -> contributor.toBuilder()
                        .skills(contributor.getSkillsList().toMap(Tuple2::_1, Tuple2::_2))
                        .build()))
                .build();
    }
}
