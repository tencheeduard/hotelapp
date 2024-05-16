package com.tencheeduard.hotelapp.controller;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.embeddables.Point;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.enums.Unit;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import com.tencheeduard.hotelapp.repositories.ReservationRepo;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import com.tencheeduard.hotelapp.services.HotelFinderService;
import com.tencheeduard.hotelapp.services.JSONParserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hotels/")
public class HotelController {

    @Autowired
    HotelFinderService hotelFinderService;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    JSONParserService jsonParserService;

    @Autowired
    ReservationRepo reservationRepo;
    @Autowired
    private RoomRepository roomRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void start() throws FileNotFoundException {
        jsonParserService.parseHotels(ResourceUtils.getFile("classpath:static/hotels.json"));
    }

    @GetMapping("/getClosest")
    public List<Hotel> getClosest(@RequestParam(name="lat") Double latitude,
                                  @RequestParam(name="long") Double longitude,
                                  @RequestParam(name="radius", defaultValue = "2") Double radius)
    {
        return hotelFinderService.getHotelsInRadius(new Point(latitude, longitude), new Distance(radius, Unit.KILOMETERS));
    }

    @GetMapping("/getDistance")
    public Distance getDistance(@RequestParam(name="lat") Double latitude,
                                @RequestParam(name="long") Double longitude,
                                @RequestParam(name="hotel") Integer hotelId,
                                HttpServletResponse response)
    {
        Distance distance = hotelFinderService.getDistanceTo(hotelId, new Point(latitude, longitude));

        if(distance == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return distance;
    }

    @GetMapping("/getRooms")
    public List<Room> getAvailableRooms(@RequestParam(name="hotel") Integer hotelId,
                                        @RequestParam(name="from") String from,
                                        @RequestParam(name="to") String to)
    {
        String[] numbers = from.split("-");
        Date fromDate = new Date(Integer.parseInt(numbers[0])-1900, Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
        numbers = to.split("-");
        Date toDate = new Date(Integer.parseInt(numbers[0])-1900, Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));

        Hotel hotel = hotelRepository.getHotelById(hotelId);

        if(hotel == null)
            return null;

        List<Room> availableRooms = roomRepository.findRoomsById_HotelAndIsAvailableIsTrue(hotel);

        if(availableRooms.isEmpty())
            return null;

        List<Room> result = new ArrayList<>();

        for(Room room : availableRooms)
        {
            if(reservationRepo.findById_RoomAndOccupiedBetween(room, fromDate, toDate).isEmpty())
                result.add(room);
        }

        return result;
    }
}
