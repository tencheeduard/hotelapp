package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.embeddables.ReservationId;
import com.tencheeduard.hotelapp.entities.Reservation;
import com.tencheeduard.hotelapp.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, ReservationId> {

    List<Reservation> findReservationsById_Room(Room room);

    @Query("SELECT res FROM Reservation res " +
            "WHERE res.id.room = :room " +
            "AND (res.id.startDate NOT BETWEEN :start AND :end)" +
            "AND (res.endDate NOT BETWEEN :start AND :end) " +
            "AND ((res.id.startDate < :start AND res.endDate < :start) OR (res.id.startDate > :end AND res.endDate > :end))")
    List<Reservation> findById_RoomAndOccupiedBetween(@Param("room")Room room,@Param("start") Date startDate,@Param("end") Date endDate);

    List<Reservation> findByAccount_Username(String username);

    List<Reservation> findByIdRoomAndAccountUsername(Room room, String username);
}
