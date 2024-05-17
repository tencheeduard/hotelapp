package com.tencheeduard.hotelapp.embeddables;

import com.tencheeduard.hotelapp.entities.Room;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ReservationId  implements Serializable {

    @ManyToOne
    Room room;

    Date startDate;

}
