package com.tencheeduard.hotelapp.services;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.config.HotelAppConfiguration;
import com.tencheeduard.hotelapp.embeddables.Point;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.enums.Unit;
import com.tencheeduard.hotelapp.geographicDistanceStrategies.GeographicDistanceStrategy;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// not entirely sure what to call this
@Service
public class GeographicCoordinateService {

    @Autowired
    GeographicDistanceStrategy strategy;

    @Autowired
    HotelRepository hotelRepository;

    public Distance distance(Point a, Point b) {
        return strategy.distance(a,b);
    }

    public boolean checkWithinDistanceFastApproximate(Point a, Point b, Distance distance)
    {
        // the idea is: there's no point in using a more complex distance calculation formula if a hotel is obviously too far away
        // these formulae calculate the distance you'd have to travel to move one degree of latitude and longitude respectively
        // if just the results of these are already bigger than the desired distance (multiplied by 2 to be sure)
        // then it's probably not worth calculating the precise distance
        // formulae are from wikipedia

        Distance distancePerLatDegree = new Distance(111132.92 - 559.82 * Math.cos(2 * a.latitude) + 1.175 * Math.cos(4 * a.latitude) - 0.0023 * Math.cos(6 * a.latitude), Unit.METERS);

        if(Math.abs((b.latitude - a.latitude) * distancePerLatDegree.getValue()) > distance.toUnit(Unit.METERS).getValue() * 2.0)
            return false;

        Distance distancePerLongDegree = new Distance(111412.84 * Math.cos(a.latitude) - 93.5 * Math.cos(3 * a.latitude) + 0.118 * Math.cos(5 * a.latitude), Unit.METERS);

        if(Math.abs((b.longitude - a.longitude) * distancePerLongDegree.getValue()) > distance.toUnit(Unit.METERS).getValue() * 2.0)
            return false;

        return true;
    }

}
