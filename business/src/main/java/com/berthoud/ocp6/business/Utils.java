package com.berthoud.ocp6.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Utils {

    private static final Logger logger = LogManager.getLogger();

    /**
     * This method takes a String as input and set the first letter as upper case
     *
     * @param input a String
     * @return the String with the first letter as upper case
     */
    public static String firstLetterUpperCase(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

}


