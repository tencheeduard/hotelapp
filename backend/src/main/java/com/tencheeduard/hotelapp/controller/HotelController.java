package com.tencheeduard.hotelapp.controller;

import com.tencheeduard.hotelapp.classes.Distance;
import com.tencheeduard.hotelapp.classes.Point;
import com.tencheeduard.hotelapp.entities.Account;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Review;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.enums.Unit;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.repositories.AccountRepository;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import com.tencheeduard.hotelapp.services.DateConverterService;
import com.tencheeduard.hotelapp.services.HotelService;
import com.tencheeduard.hotelapp.services.JSONParserService;
import com.tencheeduard.hotelapp.singletons.TimeKeeper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin("http://localhost:4200")
public class HotelController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    JSONParserService jsonParserService;

    @Autowired
    DateConverterService dateConverterService;


    // this shouldn't be here but i don't have anywhere else to put it at the moment
    @EventListener(ApplicationReadyEvent.class)
    public void start() throws FileNotFoundException {
        jsonParserService.parseHotels(ResourceUtils.getFile("classpath:static/hotels.json"));
        Account user = new Account("User", "User", "", "pass123", "email@email.com", "0000000000", TimeKeeper.getDate());
        accountRepository.save(user);
    }

    @GetMapping("/getHotelsInRadius")
    public List<Hotel> getHotelsInRadius(@RequestParam(name="lat") Double latitude,
                                  @RequestParam(name="long") Double longitude,
                                  @RequestParam(name="radius", defaultValue = "2") Double radius)
    {
        return hotelService.getHotelsInRadius(new Point(latitude, longitude), new Distance(radius, Unit.KILOMETERS));
    }

    @GetMapping("/getDistanceTo")
    public Distance getDistanceTo(@RequestParam(name="hotel") Integer hotelId,
                                @RequestParam(name="lat") Double latitude,
                                @RequestParam(name="long") Double longitude,
                                HttpServletResponse response)
    {
        try {
            return hotelService.getDistanceTo(hotelId, new Point(latitude, longitude));
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/getRooms")
    public List<Room> getAvailableRooms(@RequestParam(name="hotel") Integer hotelId,
                                        @RequestParam(name="from") String from,
                                        @RequestParam(name="to") String to,
                                        HttpServletResponse response)
    {
        Date fromDate = dateConverterService.getDate(from);
        Date toDate = dateConverterService.getDate(to);

        try{
            return hotelService.getAvailableRooms(hotelId, fromDate, toDate);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms(@RequestParam(name="hotel") Integer hotelId,
                                        HttpServletResponse response)
    {

        try{
            return hotelService.getAllRooms(hotelId);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/getRating")
    public Float getRating(@RequestParam(name="hotel") Integer hotelId,
                          HttpServletResponse response)
    {
        try{
            return hotelService.getRating(hotelId);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @GetMapping("/getReviews")
    public List<Review> getReviews(@RequestParam(name="hotel") Integer hotelId,
                                     HttpServletResponse response)
    {
        try{
            return hotelService.getReviews(hotelId);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }
}
