package com.blueur.hashcode.traffic_signaling;

import com.blueur.hashcode.common.Writer;
import com.blueur.hashcode.traffic_signaling.dto.Schedule;
import io.vavr.collection.List;

import java.io.PrintWriter;
import java.nio.file.Path;

public class SchedulesWriter extends Writer<List<Schedule>> {
    public SchedulesWriter(Path path) {
        super(path);
    }

    @Override
    protected void write(PrintWriter writer, List<Schedule> schedules) {
        writer.println(schedules.size());
        for (Schedule schedule : schedules) {
            writer.println(schedule.getIntersection().getId());
            writer.println(schedule.getLightDurations().size());
            schedule.getLightDurations().forEach((name, duration) -> {
                writer.println(name + " " + duration);
            });
        }
    }
}
