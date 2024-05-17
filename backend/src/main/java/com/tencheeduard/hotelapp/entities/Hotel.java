package com.tencheeduard.hotelapp.entities;


import com.tencheeduard.hotelapp.classes.Point;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Hotel {

    // not generatedvalue because they were assigned ids in the json
    @Id
    Integer id;

    String name;

    @Embedded
    Point coordinates;

}
