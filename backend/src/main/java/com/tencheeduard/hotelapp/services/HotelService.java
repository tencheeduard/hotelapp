package com.tencheeduard.hotelapp.services;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.classes.Point;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import com.tencheeduard.hotelapp.repositories.ReservationRepository;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    WorldCoordinateService geographicCoordinateService;


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

    public Distance getDistanceTo(Integer hotelId, Point from) throws ObjectNotFoundException {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if(hotel == null)
            throw new ObjectNotFoundException(Hotel.class);

        return geographicCoordinateService.distance(from, hotel.getCoordinates());
    }

    public List<Room> getAvailableRooms(Integer hotelId,
                                        Date from,
                                        Date to) throws ObjectNotFoundException {

        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if(hotel == null)
            throw new ObjectNotFoundException(Hotel.class);

        List<Room> availableRooms = roomRepository.findRoomsById_HotelAndIsAvailableIsTrue(hotel);

        List<Room> result = new ArrayList<>();

        for(Room room : availableRooms)
            if(reservationRepository.findById_RoomAndOccupiedBetween(room, from, to).isEmpty())
                result.add(room);

        return result;
    }

}
