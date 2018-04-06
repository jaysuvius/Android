package com.example.term.termmanager.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Validator {

    public static boolean isValidDate(String date){
        if(date == null || date == ""){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date Outdate = sdf.parse(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isValidString(String input){
        return (input != null & input.length() > 0);
    }


}
