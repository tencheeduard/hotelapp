package com.tencheeduard.hotelapp.singletons;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Logger {

    static Logger instance;

    public static Logger getInstance()
    {
        if(instance == null)
            instance = new Logger();

        return instance;
    }

    public static void log(Object what)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(TimeKeeper.getDate());
        File file = new File("logs/log-" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + ".log");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("[" + calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)+"] " + what.toString() + "\n");
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
