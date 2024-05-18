package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.embeddables.RoomId;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, RoomId> {

    List<Room> findRoomsById_HotelAndIsAvailableIsTrue(Hotel hotel);
    List<Room> findRoomsById_Hotel(Hotel hotel);

}
