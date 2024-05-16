package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.embeddables.ReservationId;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepo extends CrudRepository<Reservation, ReservationId> {

    List<Reservation> findReservationsById_Room(Room room);

    @Query("SELECT res " +
            "FROM Reservation res " +
            "WHERE res.id.room = ?1 AND (" +
            "(res.id.startDate >= ?2 AND res.id.endDate <= ?3) OR" +
            "(res.id.startDate <= ?2 AND res.id.endDate >= ?3))")
    List<Reservation> findById_RoomAndOccupiedBetween(Room room, Date from, Date to);
}
