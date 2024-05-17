package com.tencheeduard.hotelapp.repositories;

import com.tencheeduard.hotelapp.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String>{
}
