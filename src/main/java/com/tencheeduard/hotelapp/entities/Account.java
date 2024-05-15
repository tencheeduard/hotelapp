package com.tencheeduard.hotelapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
public class Account {

    @Id
    String username;

    String password;

    String email;

    String phoneNumber;

    Date joinDate;

}
