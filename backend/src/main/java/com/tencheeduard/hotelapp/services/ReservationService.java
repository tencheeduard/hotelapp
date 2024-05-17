package com.tencheeduard.hotelapp.services;


import com.tencheeduard.hotelapp.embeddables.ReservationId;
import com.tencheeduard.hotelapp.embeddables.RoomId;
import com.tencheeduard.hotelapp.entities.Account;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.exceptions.RoomOccupiedException;
import com.tencheeduard.hotelapp.repositories.AccountRepository;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import com.tencheeduard.hotelapp.repositories.ReservationRepository;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class ReservationService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    AccountRepository accountRepository;

    public Reservation makeReservation(Integer hotelId, Integer roomNumber, Date start, Date end, String username) throws ObjectNotFoundException, RoomOccupiedException {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if(hotel == null)
            throw new ObjectNotFoundException(Hotel.class);

        Room room = roomRepository.findById(new RoomId(hotel, roomNumber)).orElse(null);

        if(room == null)
            throw new ObjectNotFoundException(Room.class);

        if(!reservationRepository.findById_RoomAndOccupiedBetween(room, start, end).isEmpty())
            throw new RoomOccupiedException();

        Account account = accountRepository.findById(username).orElse(null);

        if(account == null)
            throw new ObjectNotFoundException(Account.class);

        Reservation reservation = new Reservation(new ReservationId(room, start), end, account, false);
        reservationRepository.save(reservation);

        return reservation;
    }

    public void cancelReservation(Integer roomNumber, Integer hotelId, Date start) throws ObjectNotFoundException
    {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if(hotel==null)
            throw new ObjectNotFoundException(Hotel.class);

        Room room = roomRepository.findById(new RoomId(hotel, roomNumber)).orElse(null);

        if(room==null)
            throw new ObjectNotFoundException(Room.class);

        Reservation reservation = reservationRepository.findById(new ReservationId(room, start)).orElse(null);

        if(reservation==null)
            throw new ObjectNotFoundException(Reservation.class);

        reservationRepository.delete(reservation);
    }

}
