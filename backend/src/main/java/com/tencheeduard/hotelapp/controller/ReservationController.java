package com.tencheeduard.hotelapp.controller;

import com.tencheeduard.hotelapp.entities.Account;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.exceptions.RoomOccupiedException;
import com.tencheeduard.hotelapp.services.DateConverterService;
import com.tencheeduard.hotelapp.services.ReservationService;
import com.tencheeduard.hotelapp.singletons.Logger;
import com.tencheeduard.hotelapp.singletons.TimeKeeper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
@CrossOrigin("http://localhost:4200")
public class ReservationController {

    @Autowired
    ReservationService reservationService;
    @Autowired
    DateConverterService dateConverterService;

    @PostMapping("/makeReservation")
    public void makeReservation(@RequestBody Map<String, String> reservationData, HttpServletResponse response) {
        if(!reservationData.containsKey("hotelId") ||
                !reservationData.containsKey("roomNumber") ||
                !reservationData.containsKey("start") ||
                !reservationData.containsKey("end") ||
                !reservationData.containsKey("username"))
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Integer hotelId = Integer.parseInt(reservationData.get("hotelId"));
            Integer roomNumber = Integer.parseInt(reservationData.get("roomNumber"));
            Date start = dateConverterService.getDate(reservationData.get("start"));
            Date end = dateConverterService.getDate(reservationData.get("end"));
            String username = reservationData.get("username");

            Reservation reservation = reservationService.makeReservation(hotelId, roomNumber, start, end, username);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                // switch doesn't work here for some reason
                if(e.clazz == Hotel.class)
                    response.getWriter().write("HOTEL_NOT_FOUND");
                else if(e.clazz == Room.class)
                    response.getWriter().write("ROOM_NOT_FOUND");
                else if(e.clazz == Account.class)
                    response.getWriter().write("ACCOUNT_NOT_FOUND");
            }
            // this part is not that necessary anyway, just nice to have, so can just ignore it for now
            catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
        catch(RoomOccupiedException e)
        {
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            try {
                response.getWriter().write("ROOM_OCCUPIED");
            }
            catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("/cancelReservation")
    public void cancelReservation(@RequestBody Map<String, String> reservationData, HttpServletResponse response) {
        if(!reservationData.containsKey("hotelId") ||
                !reservationData.containsKey("roomNumber") ||
                !reservationData.containsKey("start") ||
                !reservationData.containsKey("username"))
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Integer hotelId = Integer.parseInt(reservationData.get("hotelId"));
            Integer roomNumber = Integer.parseInt(reservationData.get("roomNumber"));
            Date start = dateConverterService.getDate(reservationData.get("start"));
            String username = reservationData.get("username");

            reservationService.cancelReservation(roomNumber, hotelId, start, username);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                // switch doesn't work here for some reason
                if(e.clazz == Hotel.class)
                    response.getWriter().write("HOTEL_NOT_FOUND");
                else if(e.clazz == Room.class)
                    response.getWriter().write("ROOM_NOT_FOUND");
                else if(e.clazz == Reservation.class)
                    response.getWriter().write("RESERVATION_NOT_FOUND");
            }
            // this part is not that necessary anyway, just nice to have, so can just ignore it for now
            catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @GetMapping("/getReservations")
    public List<Reservation> getReservations(@RequestParam(name="username") String username, HttpServletResponse response)
    {
        try {
            return reservationService.getReservations(username);
        }
        catch(ObjectNotFoundException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                // switch doesn't work here for some reason
                if(e.clazz == Account.class)
                    response.getWriter().write("ACCCOUNT_NOT_FOUND");
            }
            // this part is not that necessary anyway, just nice to have, so can just ignore it for now
            catch (IOException e1) {
                Logger.log(e1.getMessage());
            }
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return null;
    }

}
