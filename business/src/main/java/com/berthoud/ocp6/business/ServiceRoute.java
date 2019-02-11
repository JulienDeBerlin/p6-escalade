package com.berthoud.ocp6.business;

import com.berthoud.ocp6.model.bean.Route;

public class ServiceRoute {

    /**
     * This method takes a Route object as parameter and converts its rating originally stored as a String into an int.
     * @param route
     * @return
     */
    int getRatingAsInt (Route route){
        String fullRating = route.getRating();
        char basicRating = fullRating.charAt(0);
        return java.lang.Character.getNumericValue(basicRating);
    }
}
