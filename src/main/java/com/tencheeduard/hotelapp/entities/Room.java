package com.tencheeduard.hotelapp.entities;


import com.tencheeduard.hotelapp.embeddedIds.RoomId;
import jakarta.persistence.*;

@Entity
public class Room {

    @EmbeddedId
    RoomId hotel;

    Integer type;

    Float price;

    Boolean isAvailable;

}