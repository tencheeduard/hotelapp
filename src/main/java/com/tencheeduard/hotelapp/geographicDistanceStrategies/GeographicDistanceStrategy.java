package com.tencheeduard.hotelapp.geographicDistanceStrategies;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.embeddables.Point;

public interface GeographicDistanceStrategy {

    // returns a distance in km
    public Distance distance(Point a, Point b);

}
