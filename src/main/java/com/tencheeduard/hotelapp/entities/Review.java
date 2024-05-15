package com.tencheeduard.hotelapp.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
public class Review {

    @Id
    Integer reviewId;

    // room also contains information about the hotel that the reviewer stayed in
    @ManyToOne
    Room room;

    @ManyToOne
    Account account;

    // out of 5
    Byte rating;

    @Lob
    @Column
    String body;

    Date date;

}
