package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Writer;
import com.blueur.hashcode.mentorship.dto.Schedule;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ScheduleWriter extends Writer<Schedule> {
    public ScheduleWriter(Path path) {
        super(path);
    }

    @Override
    protected void write(PrintWriter writer, Schedule output) {
        writer.println(output.getAssignments().length());
        output.getAssignments().forEach(assignment -> {
            writer.println(assignment.getId());
            writer.println(assignment.getRoles().collect(Collectors.joining(" ")));
        });
    }
}
