package com.tencheeduard.hotelapp.entities;


import com.tencheeduard.hotelapp.embeddables.RoomId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Room {

    @EmbeddedId
    RoomId id;

    Integer type;

    Float price;

    Boolean isAvailable;

}