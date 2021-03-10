package com.blueur.hashcode.traffic_signaling;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.traffic_signaling.dto.Car;
import com.blueur.hashcode.traffic_signaling.dto.Input;
import com.blueur.hashcode.traffic_signaling.dto.Intersection;
import com.blueur.hashcode.traffic_signaling.dto.Street;
import io.vavr.collection.SortedMap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

public class CityParser extends Parser<Input> {
    public CityParser(Path path) throws IOException {
        super(path);
    }

    @Override
    public Input parse() throws IOException {
        Input input = new Input();
        readLine(input,
                integer(Input::setDuration),
                integer(Input::setIntersectionsCount),
                integer(Input::setStreetsCount),
                integer(Input::setCarsCount),
                integer(Input::setScore)
        );
        fillList(input, Input::setIntersections, input.getIntersectionsCount(), Intersection::new);
        readLines(input, Input::setStreets, input.getStreetsCount(),
                parseElement(Street::new,
                        integer((street, integer) -> street.setStart(input.getIntersections().get(integer))),
                        integer((street, integer) -> street.setEnd(input.getIntersections().get(integer))),
                        Street::setName,
                        integer(Street::setLength)
                )
        );
        final SortedMap<String, Street> nameStreets = input.getStreets()
                .toSortedMap(Street::getName, Function.identity());
        readLines(input, Input::setCars, input.getCarsCount(),
                parseElementList(Car::new,
                        Car::setPaths, s -> nameStreets.get(s).get()
                )
        );
        return input;
    }
}
