package com.blueur.hashcode.traffic_signaling.solver;

import com.blueur.hashcode.common.Solver;
import com.blueur.hashcode.traffic_signaling.dto.City;
import com.blueur.hashcode.traffic_signaling.dto.Schedule;
import com.blueur.hashcode.traffic_signaling.dto.Street;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.List;

public class NaiveSolver extends Solver<City, List<Schedule>> {
    @Override
    public List<Schedule> solve(City city) {
        return city.getIntersections()
                .map(intersection -> {
                    final LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) intersection
                            .getIncomingStreets()
                            .toLinkedMap(Street::getName, street -> 1);
                    return Schedule.builder()
                            .intersection(intersection)
                            .lightDurations(map)
                            .build();
                });
    }
}
