package com.tencheeduard.hotelapp.entities;

import com.tencheeduard.hotelapp.embeddables.ReservationId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Reservation {

    @EmbeddedId
    ReservationId id;

    @Temporal(TemporalType.DATE)
    Date endDate;

    @ManyToOne
    Account account;

    Boolean cancelled;
}
