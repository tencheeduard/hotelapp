package com.tencheeduard.hotelapp.geographicDistanceStrategies;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.embeddables.Point;
import com.tencheeduard.hotelapp.enums.Unit;

public class HaversineFormulaStrategy implements GeographicDistanceStrategy
{
    public final Distance EARTH_RADIUS = new Distance(6378.1370, Unit.KILOMETERS) ;

    Double haversine(Double value)
    {
        return Math.pow(Math.sin(value / 2), 2);
    }

    // courtesy of baeldung.com
    // not all that accurate but it is fast
    @Override
    public Distance distance(Point a, Point b) {

        Double latitudeDistance = Math.toRadians((b.latitude - a.latitude));
        Double longitudeDistance = Math.toRadians((b.longitude - a.longitude));

        Double c = haversine(latitudeDistance) + Math.cos(Math.toRadians(a.latitude)) * Math.cos(Math.toRadians(b.longitude)) * haversine(longitudeDistance);
        Double d = 2 * Math.atan2(Math.sqrt(c), Math.sqrt(1-c));

        return new Distance(EARTH_RADIUS.getValue() * d, EARTH_RADIUS.getUnit());
    }
}
