package com.berthoud.ocp6.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;


public class Utils {

    private static final Logger logger = LogManager.getLogger();


    public static String firstLetterUpperCase(String input){
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }

    // Exception not handled yet!!!!
    public static LocalDate convertStringIntoDate(String date) {
        LocalDate myDate = LocalDate.parse(date);
        return myDate;
    }

}


