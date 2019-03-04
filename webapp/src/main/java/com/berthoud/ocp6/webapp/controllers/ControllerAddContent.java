package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.business.ServiceRoute;
import com.berthoud.ocp6.business.ServiceSpot;
import com.berthoud.ocp6.business.ServiceSpotComment;
import com.berthoud.ocp6.model.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes(value= {"user", "selectedLocation", "selectedLocationToBeStored", "selectedSpot"})
public class ControllerAddContent {

    @Autowired
    ServiceLocation serviceLocation ;
    @Autowired
    ServiceSpot serviceSpot;
    @Autowired
    ServiceRoute serviceRoute;
    @Autowired
    ServiceSpotComment serviceSpotComment;


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
    public String displaysFormNewSpot (@RequestParam (value = "cityNameInput") String cityName,
                           @RequestParam (value = "departementName") String departementName,
                           @RequestParam (value = "departementId") int departementId,
                           @RequestParam (value = "region") String region,
                           @RequestParam (value = "codePostal") String zipCode,
                            ModelMap model) {

        Location locationDB = serviceLocation.findLocationByNameAndDepartement(cityName, departementName);

        if (locationDB == null){
            Location newLocation = new Location();
            newLocation.setCityName(cityName);
            newLocation.setDepartementId(departementId);
            newLocation.setDepartementName(departementName);
            newLocation.setRegion(region);
            newLocation.setZipCode(zipCode);

            model.put("selectedLocation", newLocation);
            model.put("selectedLocationToBeStored", true);

        }else {
            model.put("selectedLocation", locationDB);
            model.put("selectedLocationToBeStored", false);

        }

        model.put("step", "step2");

    return "newSpot";

    }


    @RequestMapping(value = "addcontent/newSpot", method = RequestMethod.POST)
    public String addNewSpot(@RequestParam(value = "nameSpot") String nameSpot,
                             @RequestParam(value = "nameArea") String nameArea,
                             @RequestParam(value = "comment") String comment,
                             @SessionAttribute(value = "selectedLocation") Location selectedLocation,
                             @SessionAttribute(value = "selectedLocationToBeStored") boolean selectedLocationToBeStored,
                             @SessionAttribute (value = "user") Member user,
                             ModelMap model) {

        Spot newSpotWithoutKey = new Spot();
        newSpotWithoutKey.setNameSpot(nameSpot);
        newSpotWithoutKey.setNameArea(nameArea);
        Spot newSpotWithKey;

        if (!selectedLocationToBeStored) {
            newSpotWithoutKey.setLocation(selectedLocation);
            newSpotWithKey = serviceSpot.insertSpot(newSpotWithoutKey);

        } else {
            newSpotWithKey = serviceLocation.insertLocationAndItsSpot(selectedLocation,newSpotWithoutKey);
            model.put("selectedLocationToBeStored", false);
        }

        selectedLocation = serviceLocation.findLocationByNameAndDepartement(selectedLocation.getCityName(), selectedLocation.getDepartementName());
        model.put("selectedLocation", selectedLocation);

        SpotComment newCommentWithoutKey = new SpotComment();
        newCommentWithoutKey.setComment(comment);
        newCommentWithoutKey.setMember(user);
        newCommentWithoutKey.setSpot(newSpotWithKey);

        serviceSpotComment.insertSpotComment(newCommentWithoutKey);

        model.put("step", "step2");

        return "newSpot";
    }


    @RequestMapping(value = "addcontent/formNewRoute", method = RequestMethod.POST)
    public String displaysFormNewRoute (@RequestParam (value = "selectedSpot") int IdSelectedSpot,
                                        ModelMap model) {

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(IdSelectedSpot);
        model.put ("selectedSpot", selectedSpot);
        model.put("step", "step3");
        return "newSpot";
    }


    @RequestMapping(value = "addcontent/newRoute", method = RequestMethod.POST)
    public String displaysFormNewRoute (@RequestParam (value = "name") String nameRoute,
                                        @RequestParam (value = "nbPitch") byte nbPitch,
                                        @RequestParam (value = "indexPitch") byte indexPitch,
                                        @RequestParam (value = "rating") String rating,
                                        @RequestParam (value = "isBolted", required = false) boolean isBolted,
                                        @SessionAttribute (value = "selectedSpot") Spot selectedSpot,
                                        ModelMap model)
    {
        Route newRoute = new Route();
        newRoute.setName(nameRoute);
        newRoute.setNbPitch(nbPitch);
        newRoute.setIndexPitch(indexPitch);
        newRoute.setBolted(isBolted);
        newRoute.setRating(rating);
        newRoute.setSpot(selectedSpot);

        serviceRoute.insertRoute(newRoute);

        selectedSpot = serviceSpot.findSpotBasedOnId(selectedSpot.getId());

        model.put("message", "ok");
        model.put("step", "step3");
        model.put ("selectedSpot", selectedSpot);

        return "newSpot";
    }



}

