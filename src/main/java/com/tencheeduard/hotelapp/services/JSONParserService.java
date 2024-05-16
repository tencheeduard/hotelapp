package com.tencheeduard.hotelapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencheeduard.hotelapp.HotelappApplication;
import com.tencheeduard.hotelapp.embeddables.Point;
import com.tencheeduard.hotelapp.embeddables.RoomId;
import com.tencheeduard.hotelapp.entities.Hotel;
import com.tencheeduard.hotelapp.entities.Room;
import com.tencheeduard.hotelapp.repositories.HotelRepository;
import com.tencheeduard.hotelapp.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JSONParserService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private HotelappApplication hotelappApplication;

    public void parseHotels(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<HashMap<String, Object>> list = mapper.readValue(file, new TypeReference<>() {
            });

            for (HashMap<String, Object> map : list) {
                try {
                    String name = (String) map.get("name");
                    Integer id = (Integer) map.get("id");
                    Double latitude = (Double) map.get("latitude");
                    Double longitude = (Double) map.get("longitude");
                    List<HashMap<String, Object>> rooms = (ArrayList<HashMap<String, Object>>) map.get("rooms");

                    Hotel hotel = new Hotel(id, name, new Point(latitude, longitude));
                    hotelRepository.save(hotel);

                    for (HashMap<String, Object> room : rooms) {
                        Integer roomNumber = (Integer) room.get("roomNumber");
                        Integer type = (Integer) room.get("type");
                        Float price = (float) ((int) room.get("price"));
                        Boolean isAvailable = (Boolean) room.get("isAvailable");

                        roomRepository.save(new Room(new RoomId(hotel, roomNumber), type, price, isAvailable));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
