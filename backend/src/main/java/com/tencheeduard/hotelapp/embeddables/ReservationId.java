package com.tencheeduard.hotelapp.embeddables;

import com.tencheeduard.hotelapp.entities.Room;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ReservationId  implements Serializable {

    @ManyToOne
    Room room;

    @Temporal(TemporalType.DATE)
    Date startDate;

}
