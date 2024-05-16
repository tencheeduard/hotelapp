package com.tencheeduard.hotelapp.entities;


import com.tencheeduard.hotelapp.embeddables.Point;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Hotel {

    @Id
    Integer id;

    String name;

    @Embedded
    Point coordinates;

}
