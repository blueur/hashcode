package com.blueur.hashcode.traffic_signaling;

import com.blueur.hashcode.common.Writer;
import com.blueur.hashcode.traffic_signaling.dto.Schedule;
import io.vavr.collection.List;

import java.io.IOException;
import java.nio.file.Path;

public class SignalingWriter extends Writer<List<Schedule>> {
    @Override
    public void write(Path path, List<Schedule> output) throws IOException {

    }
}
