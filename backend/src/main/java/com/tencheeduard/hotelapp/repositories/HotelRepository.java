package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.entities.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {
}
