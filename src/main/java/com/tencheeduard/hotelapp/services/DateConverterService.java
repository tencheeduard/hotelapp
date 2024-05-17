package com.tencheeduard.hotelapp.services;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DateConverterService {

    public String[] validFormats =
    {
            "dd-MM-yyyy",
            "dd-MM-yyyy HH:mm",
            "dd-MM-yyyy HH:mm z",
            "dd-MM-yyyy HH:mm:ss",
            "dd-MM-yyyy HH:mm:ss z",
            "dd/MM/yyyy",
            "dd/MM/yyyy HH:mm",
            "dd/MM/yyyy HH:mm z",
            "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm:ss z"
    };

    public Date getDate(String dateStr)
    {
        Date date = null;

        for(String format : validFormats)
        {
            date = tryGetDate(dateStr, format);

            if(date!=null)
                break;
        }

        return date;
    }

    public Date tryGetDate(String dateStr, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            // cast from util.date to sql.date
             date = new Date(formatter.parse(dateStr).getTime());
        }
        catch(ParseException e)
        {
        }

        return date;
    }

}
