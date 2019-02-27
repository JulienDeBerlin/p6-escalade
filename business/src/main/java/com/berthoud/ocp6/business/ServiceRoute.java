package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.RouteDao;
import com.berthoud.ocp6.model.bean.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ServiceRoute {

    @Autowired
    RouteDao routeDao;

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


    @Transactional
    public Route insertRoute(Route route) {
        return (routeDao.insertRoute(route));
    }

}
