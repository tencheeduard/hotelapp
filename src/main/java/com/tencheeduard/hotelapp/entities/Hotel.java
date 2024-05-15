package com.tencheeduard.hotelapp.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Hotel {

    @Id
    Integer id;

    String name;

    Double longitude;

    Double latitude;


}
