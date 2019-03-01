package com.berthoud.ocp6.business;

public class Utils {

    public static String firstLetterUpperCase(String input){
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }


}
