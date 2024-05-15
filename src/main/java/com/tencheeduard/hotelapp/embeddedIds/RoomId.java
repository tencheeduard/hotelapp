package com.tencheeduard.hotelapp.embeddedIds;


import com.tencheeduard.hotelapp.entities.Hotel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RoomId implements Serializable {

    @ManyToOne
    Hotel hotel;

    Integer roomNumber;

}
