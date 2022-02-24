package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.mentorship.dto.Contributor;
import com.blueur.hashcode.mentorship.dto.Team;

import java.nio.file.Path;
import java.util.Iterator;

public class TeamParser extends Parser<Team> {

    public TeamParser(Path path) {
        super(path);
    }

    @Override
    public Team parseIterator(Iterator<String> fileIterator) {
        return object(Team::new,
                line(
                        field(integer(Team::setContributorsCount)),
                        field(integer(Team::setProjectsCount))
                ),
                list(Team::getContributorsCount, Team::setContributors, team -> object(Contributor::new,
                        line(
                                field(Contributor::setId),
                                field(integer(Contributor::setSkillsCount))
                        ),
                        sortedMap(Contributor::getSkillsCount, Contributor::setSkills, integer ->),
                        ))
        ).apply(fileIterator);
    }
}
