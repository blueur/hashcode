package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Executor;
import com.blueur.hashcode.mentorship.dto.Schedule;
import com.blueur.hashcode.mentorship.dto.Team;
import com.blueur.hashcode.mentorship.solver.NaiveSolver;
import com.blueur.hashcode.mentorship.solver.ParallelLearningSolver;
import com.blueur.hashcode.mentorship.solver.ParallelNaiveSolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MentorshipApplication {
    public static void main(String[] args) throws Throwable {
        final Executor<Team, Schedule> executor = Executor.<Team, Schedule>builder()
                .application(MentorshipApplication.class)
                .problemFolder("../problem/2022-mentorship")
                .parser(TeamParser::new)
                .solver(ParallelLearningSolver::new)
                .writer(ScheduleWriter::new)
                .build();
//        executor.execute("a_an_example.in.txt");
//        executor.execute("b_better_start_small.in.txt");
        executor.zipSource();
        executor.executeAll();
    }
}
