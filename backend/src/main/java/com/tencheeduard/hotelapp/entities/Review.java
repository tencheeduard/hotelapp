package com.tencheeduard.hotelapp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Temporal(TemporalType.DATE)
    Date date;

    Boolean isPublic;

}
