package com.blueur.hashcode.mentorship;

import com.blueur.hashcode.common.Writer;
import com.blueur.hashcode.mentorship.dto.Schedule;

import java.io.PrintWriter;
import java.nio.file.Path;

public class ScheduleWriter extends Writer<Schedule> {
    public ScheduleWriter(Path path) {
        super(path);
    }

    @Override
    protected void write(PrintWriter writer, Schedule output) {
        writer.print(output.getAssignmentsCount());
    }
}
