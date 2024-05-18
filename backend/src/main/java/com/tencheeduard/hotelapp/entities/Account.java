package com.tencheeduard.hotelapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    String username;

    String firstName;

    String lastName;

    String password;

    String email;

    String phoneNumber;

    Date joinDate;

}