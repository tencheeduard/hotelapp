package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.entities.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
