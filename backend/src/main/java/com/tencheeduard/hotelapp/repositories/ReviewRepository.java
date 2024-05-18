package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    public List<Review> findAllByRoom_IdHotel(Hotel hotel);

    public List<Review> findAllByRoom_IdHotelAndIsPublicTrue(Hotel hotel);

}
