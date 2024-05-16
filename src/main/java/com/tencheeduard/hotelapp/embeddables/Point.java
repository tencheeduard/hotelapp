package com.tencheeduard.hotelapp.embeddables;


import com.tencheeduard.hotelapp.classes.Distance;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    public Double latitude;
    public Double longitude;

}
