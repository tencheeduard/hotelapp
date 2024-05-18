package com.tencheeduard.hotelapp.services;


import com.tencheeduard.hotelapp.embeddables.ReservationId;
import com.tencheeduard.hotelapp.embeddables.RoomId;
import com.tencheeduard.hotelapp.entities.Account;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.exceptions.CancellationNotAllowedException;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.exceptions.RoomOccupiedException;
import com.tencheeduard.hotelapp.repositories.AccountRepository;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import com.tencheeduard.hotelapp.repositories.ReservationRepository;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import com.tencheeduard.hotelapp.singletons.TimeKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    public void cancelReservation(Integer roomNumber, Integer hotelId, Date start, String username) throws ObjectNotFoundException, CancellationNotAllowedException {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        if(hotel==null)
            throw new ObjectNotFoundException(Hotel.class);

        Room room = roomRepository.findById(new RoomId(hotel, roomNumber)).orElse(null);

        if(room==null)
            throw new ObjectNotFoundException(Room.class);

        // not good, but im running out of time and this will have to do
        Reservation reservation = reservationRepository.findByIdRoomAndAccountUsername(room, username).stream().filter((x)->x.getId().getStartDate().getDate()==start.getDate()&&x.getId().getStartDate().getMonth()==start.getMonth()&&x.getId().getStartDate().getYear()==start.getYear()).findFirst().orElse(null);

        if(reservation==null)
            throw new ObjectNotFoundException(Reservation.class);

        //                                                                          2 hours
        if(TimeKeeper.getDate().getTime() > reservation.getEndDate().getTime() - 3600000)
            throw new CancellationNotAllowedException();

        reservation.setCancelled(true);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations(String username) throws ObjectNotFoundException {
        Account account = accountRepository.findById(username).orElse(null);

        if(account==null)
            throw new ObjectNotFoundException(Account.class);

        return reservationRepository.findByAccount_Username(account.getUsername());
    }

}
