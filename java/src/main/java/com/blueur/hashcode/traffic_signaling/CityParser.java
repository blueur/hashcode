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
                        integer(City::setDuration),
                        integer(City::setIntersectionsCount),
                        integer(City::setStreetsCount),
                        integer(City::setCarsCount),
                        integer(City::setScore)
                ),
                idList(City::getIntersectionsCount, City::setIntersections, city -> idObject(Intersection::new)),
                sortedMap(City::getStreetsCount, City::setStreets, Street::getName, city -> object(Street::new,
                        line(
                                integer((street, i) -> street.setStart(city.getIntersections().get(i))),
                                integer((street, i) -> street.setEnd(city.getIntersections().get(i))),
                                string(Street::setName),
                                integer(Street::setLength)
                        )
                )),
                idList(City::getCarsCount, City::setCars, city -> idObject(Car::new,
                        line(
                                integer(Car::setPathsCount),
                                idList(Car::getPathsCount, Car::setPaths, car -> iterator -> integer -> city.getStreets().get(iterator.next()).get())
                        )
                ))
        ).apply(fileIterator);
    }
}
