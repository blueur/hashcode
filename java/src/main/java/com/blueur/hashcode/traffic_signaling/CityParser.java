package com.blueur.hashcode.traffic_signaling;

import com.blueur.hashcode.common.Parser;
import com.blueur.hashcode.traffic_signaling.dto.Car;
import com.blueur.hashcode.traffic_signaling.dto.City;
import com.blueur.hashcode.traffic_signaling.dto.Intersection;
import com.blueur.hashcode.traffic_signaling.dto.Street;

import java.nio.file.Path;
import java.util.Iterator;

public class CityParser extends Parser<City> {
    public CityParser(Path path) {
        super(path);
    }

    @Override
    public City parseIterator(Iterator<String> fileIterator) {
        return object(City::new,
                line(
                        field(integer(City::setDuration)),
                        field(integer(City::setIntersectionsCount)),
                        field(integer(City::setStreetsCount)),
                        field(integer(City::setCarsCount)),
                        field(integer(City::setScore))
                ),
                list(City::getIntersectionsCount, City::setIntersections, city -> object(Intersection::new)),
                sortedMap(City::getStreetsCount, City::setStreets, Street::getName, city -> object(Street::new,
                        line(
                                field(integer((street, i) -> street.setStart(city.getIntersections().get(i)))),
                                field(integer((street, i) -> street.setEnd(city.getIntersections().get(i)))),
                                field(Street::setName),
                                field(integer(Street::setLength))
                        )
                )),
                list(City::getCarsCount, City::setCars, city -> object(Car::new,
                        line(
                                field(integer(Car::setPathsCount)),
                                list(Car::getPathsCount, Car::setPaths, car -> iterator -> integer -> city.getStreets().get(iterator.next()).get())
                        )
                ))
        ).apply(fileIterator);
    }
}
