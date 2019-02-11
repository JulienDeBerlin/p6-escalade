package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.model.bean.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.lang.Integer.parseInt;


@Controller
public class ControllerSpots {

    @Autowired
    ServiceLocation serviceLocation;

    private static final Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public String getResultSpots(@RequestParam (value = "locationInput") String locationInput,
                                 @RequestParam (value="onlySpotsWithBoltedRoutes", required = false) boolean onlySpotsWithBoltedRoutes,
                                 @RequestParam (value = "rating") String rating,
                                 ModelMap model) {

        List<Location> resultLocations =serviceLocation.detailledInfoBasedOnLocation(locationInput, "departement_name");
        resultLocations = serviceLocation.filterLocation(resultLocations, onlySpotsWithBoltedRoutes, parseInt(rating));
        model.put("resultLocations", resultLocations);
        return "spots";
    }

}
