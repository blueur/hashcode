package com.blueur.hashcode.mentorship.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.mentorship.dto.Schedule;
import com.blueur.hashcode.mentorship.dto.Team;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaiveSolver extends Solver<Team, Schedule> {
    @Override
    public Schedule solve(Team input) {
        return new Schedule();
    }
}
