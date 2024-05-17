package com.tencheeduard.hotelapp.embeddables;


import com.tencheeduard.hotelapp.entities.Hotel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter @Setter
public class RoomId implements Serializable {

    @ManyToOne
    Hotel hotel;

    Integer roomNumber;

}
