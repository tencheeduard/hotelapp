package com.tencheeduard.hotelapp.services;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.embeddables.Point;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelFinderService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    GeographicCoordinateService geographicCoordinateService;


    // returns ids of closest hotels
    public List<Hotel> getHotelsInRadius(Point center, Distance radius)
    {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        List<Hotel> resultList = new ArrayList<>();

        for(Hotel hotel : hotels) {
            // if the hotel is obviously too far away, don't bother calculating the exact distance
            if(!geographicCoordinateService.checkWithinDistanceFastApproximate(center, hotel.getCoordinates(), radius))
                break;

            // if not, actually check the distance
            if (geographicCoordinateService.distance(hotel.getCoordinates(), center).compare(radius) < 0) {
                resultList.add(hotel);
            }
        }

        return resultList;
    }

    public Distance getDistanceTo(Integer hotelId, Point from)
    {
        Hotel hotel = hotelRepository.getHotelById(hotelId);

        if(hotel == null)
            return null;

        return geographicCoordinateService.distance(from, hotel.getCoordinates());
    }

}
