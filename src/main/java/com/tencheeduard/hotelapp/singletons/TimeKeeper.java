package com.tencheeduard.hotelapp.singletons;


import java.sql.Date;

// this isn't *really* necessary, but it's helpful for debugging and also to demonstrate the tool, since it allows you to change the current time
public class TimeKeeper {

    static TimeKeeper instance;

    long timeOffset;

    TimeKeeper()
    {
        timeOffset = 0;
    }

    public static TimeKeeper getInstance()
    {
        if(instance == null)
            instance = new TimeKeeper();

        return instance;
    }

    public static Date getDate()
    {
        return new Date(System.currentTimeMillis() + getInstance().timeOffset);
    }

    public static void changeDate(Date newDate)
    {
        getInstance().timeOffset = newDate.getTime() - System.currentTimeMillis();
    }

    public static void resetDate()
    {
        getInstance().timeOffset = 0;
    }

}