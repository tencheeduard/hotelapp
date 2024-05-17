package com.tencheeduard.hotelapp.exceptions;

public class ObjectNotFoundException extends Exception {

    public Class<?> clazz;
    public ObjectNotFoundException(Class<?> clazz) {
        this.clazz = clazz;
    }

}
