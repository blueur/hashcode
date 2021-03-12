package com.blueur.hashcode.traffic_signaling;

import com.blueur.hashcode.common.Executor;
import com.blueur.hashcode.traffic_signaling.dto.City;
import com.blueur.hashcode.traffic_signaling.dto.Schedule;
import io.vavr.collection.List;

public class TrafficSignalingApplication {
    public static void main(String[] args) throws Throwable {
        final Executor<City, List<Schedule>> executor = Executor.<City, List<Schedule>>builder()
                .folder("problem/2021-traffic_signaling")
                .parser(CityParser::new)
                .solver(new TrafficSolver())
                .writer(new SignalingWriter())
                .build();
        executor.execute("a.txt");
//        executor.executeAll();
    }
}