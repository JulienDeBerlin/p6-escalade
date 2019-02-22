package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.model.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes(value= {"user"})
public class ControllerAddContent {


    @RequestMapping(value = "addcontent/spot", method = RequestMethod.GET)
    public String goToAddSpot (ModelMap model) {
        if (model.containsAttribute("user")) {
            return "newSpot";
        }
        else {
            String message = "onlyMembers";
            model.put("message", message);

            model.put("jspAfterLogin", "newSpot");

            return "login";
        }
    }

    @RequestMapping(value = "addcontent/spot", method = RequestMethod.POST)
    public String AddSpot (@RequestParam (value = "cityNameInput") String cityName,
                           @RequestParam (value = "departementName") String departementName,
                           @RequestParam (value = "departementId") int departementId,
                           @RequestParam (value = "region") String region,
                           @RequestParam (value = "codePostal") String zipCode,
                            ModelMap model) {

        Location location = new Location();
        location.setCityName(cityName);
        location.setDepartementId(departementId);
        location.setDepartementName(departementName);
        location.setRegion(region);
        location.setZipCode(zipCode);

        model.put("selectedLocation", location);

    return "newSpot";

    }







}

