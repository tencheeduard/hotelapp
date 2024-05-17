package com.tencheeduard.hotelapp.controller;


import com.tencheeduard.hotelapp.entities.Account;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Review;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.exceptions.ObjectNotFoundException;
import com.tencheeduard.hotelapp.services.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/leaveReview")
    public void leaveReview(@RequestBody Map<String,String> reviewData, HttpServletResponse response)
    {
        if(!reviewData.containsKey("username") ||
            !reviewData.containsKey("roomId") ||
            !reviewData.containsKey("hotelId") ||
            !reviewData.containsKey("body") ||
            !reviewData.containsKey("rating") ||
            !reviewData.containsKey("isPublic"))
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String username = reviewData.get("username");
            Integer roomId = Integer.parseInt(reviewData.get("roomId"));
            Integer hotelId = Integer.parseInt(reviewData.get("hotelId"));
            String body = reviewData.get("body");
            Byte rating = Byte.parseByte(reviewData.get("rating"));
            Boolean isPublic = Boolean.parseBoolean(reviewData.get("isPublic"));

            Review review = reviewService.createReview(username, roomId, hotelId, body, rating, isPublic);
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
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}