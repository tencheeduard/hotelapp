package com.tencheeduard.hotelapp;

import com.tencheeduard.hotelapp.singletons.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelappApplication {

	public static void main(String[] args) {
		Logger.log("Started Application");
		SpringApplication.run(HotelappApplication.class, args);
	}


}
