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


@Controller
@SessionAttributes(value = {"user", "selectedLocation", "selectedLocationToBeStored", "selectedSpot"})
public class ControllerAddContent {

    @Autowired
    ServiceLocation serviceLocation;
    @Autowired
    ServiceSpot serviceSpot;
    @Autowired
    ServiceRoute serviceRoute;
    @Autowired
    ServiceSpotComment serviceSpotComment;


    /**
     * This controller-method checks if a member is logged in the session. If yes, the page "newSpot" is displayed.
     * If no, the user is redirected to the login page.
     *
     * @param model //
     * @return newSpot.jsp, the view dedicated to the referencing of new spots and routes or login.jsp
     */
    @RequestMapping(value = "/addcontent/spot", method = RequestMethod.GET)
    public String goToAddSpot(ModelMap model) {
        if (model.containsAttribute("user")) {
            return "newSpot";
        } else {
            model.put("message", "onlyMembers");

            model.put("jspAfterLogin", "newSpot");

            return "login";
        }
    }


    /**
     * This controller-method is used in the process of registering a new spot. The user must first enter the
     * name of the city where the spot to be registered is located. Based on this, the other data related to the location such as departement, region...
     * are retrieved from the API {@linktourl https://geo.api.gouv.fr/} and set in hidden fields of the
     * form in the JSP. All these form fields are then retrieved by this method.
     * <p>
     * This controller-method checks if the entered location is already stored in the DB and set the spot-registration process at step2, so that
     * step 2 can be displayed on the page.
     *
     * @param cityName        user input (autocomplete in newSpot.jsp)
     * @param departementName retrieved from API (autocomplete in newSpot.jsp)
     * @param departementId   retrieved from API (autocomplete in newSpot.jsp)
     * @param region          retrieved from API (autocomplete in newSpot.jsp)
     * @param zipCode         retrieved from API (autocomplete in newSpot.jsp)
     * @param model           //
     * @return newSpot.jsp, the view dedicated to the referencing of new spots and routes
     */
    @RequestMapping(value = "/addcontent/spot/checkCityInput", method = RequestMethod.POST)
    public String checkCityInput(@RequestParam(value = "cityNameInput") String cityName,
                                 @RequestParam(value = "departementName") String departementName,
                                 @RequestParam(value = "departementId") String departementId,
                                 @RequestParam(value = "region") String region,
                                 @RequestParam(value = "codePostal") String zipCode,
                                 ModelMap model) {

        Location locationDB = serviceLocation.findLocationByNameAndDepartement(cityName, departementName);

        if (locationDB == null) {
            Location newLocation = new Location();
            newLocation.setCityName(cityName);
            newLocation.setDepartementId(departementId);
            newLocation.setDepartementName(departementName);
            newLocation.setRegion(region);
            newLocation.setZipCode(zipCode);

            model.put("selectedLocation", newLocation);
            model.put("selectedLocationToBeStored", true);

        } else {
            model.put("selectedLocation", locationDB);
            model.put("selectedLocationToBeStored", false);
        }

        model.put("step", "step2");

        return "newSpot";

    }


    /**
     * This controller-method is used to store location and spots (incl. related comments) in the DB.
     * Note that if the location entered by the user doesn't exist yet in the DB, it will be insert in the DB, but only in combination with
     * the first spot linked to this location. This is meant to avoid having location without spots in the DB.
     *
     * @param nameSpot                   entered by the user
     * @param nameArea                   entered by the user
     * @param comment                    entered by the user
     * @param selectedLocation           the location that had been entered by the user at the beginning of the spot-process-registration
     * @param selectedLocationToBeStored is TRUE if the location doesn't exist yet in the DB, is FALSE otherwise
     * @param user                       the user
     * @param model                      //
     * @return newSpot.jsp, the view dedicated to the referencing of new spots and routes
     */
    @RequestMapping(value = "/addcontent/addNewSpot", method = RequestMethod.POST)
    public String addNewSpot(@RequestParam(value = "nameSpot") String nameSpot,
                             @RequestParam(value = "nameArea") String nameArea,
                             @RequestParam(value = "spotAccess") String access,
                             @RequestParam(value = "comment") String comment,
                             @SessionAttribute(value = "selectedLocation") Location selectedLocation,
                             @SessionAttribute(value = "selectedLocationToBeStored") boolean selectedLocationToBeStored,
                             @SessionAttribute(value = "user") Member user,
                             ModelMap model) {

        Spot newSpotWithoutKey = new Spot();
        newSpotWithoutKey.setNameSpot(nameSpot);
        newSpotWithoutKey.setNameArea(nameArea);
        newSpotWithoutKey.setAccess(access);
        Spot newSpotWithKey;

        if (!selectedLocationToBeStored) {
            newSpotWithoutKey.setLocation(selectedLocation);
            newSpotWithKey = serviceSpot.insertSpot(newSpotWithoutKey);

        } else {
            newSpotWithKey = serviceLocation.insertLocationAndItsSpot(selectedLocation, newSpotWithoutKey);
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


    /**
     * This controller-method is used in the process of registration of a new route for a spot. The user selects through a select-form the spot he
     * is willing to add a route to. The Id of the selected spot is then passed to this method.
     *
     * @param IdSelectedSpot The Id of the spot that the user wants to add a route to.
     * @param model          //
     * @return
     */
    @RequestMapping(value = "/addcontent/formNewRoute", method = RequestMethod.POST)
    public String displaysFormNewRoute(@RequestParam(value = "selectedSpot") int IdSelectedSpot,
                                       ModelMap model) {

        Spot selectedSpot = serviceSpot.findSpotBasedOnId(IdSelectedSpot);
        model.put("selectedSpot", selectedSpot);
        model.put("step", "step3");
        return "newSpot";
    }


    /**
     * This controller-method is used in the process of registration of a new route for a spot.
     * It retrieves the attributes of a new route, as entered by the user and insert the new route in the DB.
     *
     * @param nameRoute    as entered by the user in the form
     * @param nbPitch      as entered by the user in the form
     * @param indexPitch   as entered by the user in the form
     * @param rating       as entered by the user in the form
     * @param selectedSpot the spot related to the route
     * @param nbAnchor     the nb of anchors (0 if the route is not bolted)
     * @param model        //
     * @return
     */
    @RequestMapping(value = "/addcontent/newRoute", method = RequestMethod.POST)
    public String displaysFormNewRoute(@RequestParam(value = "name") String nameRoute,
                                       @RequestParam(value = "nbPitch") byte nbPitch,
                                       @RequestParam(value = "indexPitch") byte indexPitch,
                                       @RequestParam(value = "rating") String rating,
//                                       @RequestParam(value = "isBolted", required = false) boolean isBolted,
                                       @RequestParam(value = "nbAnchor") int nbAnchor,
                                       @SessionAttribute(value = "selectedSpot") Spot selectedSpot,
                                       ModelMap model) {
        Route newRoute = new Route();
        newRoute.setName(nameRoute);
        newRoute.setNbPitch(nbPitch);
        newRoute.setIndexPitch(indexPitch);
//        newRoute.setBolted(isBolted);
        newRoute.setRating(rating);
        newRoute.setNbAnchor(nbAnchor);
        newRoute.setSpot(selectedSpot);

        serviceRoute.insertRoute(newRoute);

        selectedSpot = serviceSpot.findSpotBasedOnId(selectedSpot.getId());

        model.put("message", "ok");
        model.put("step", "step3");
        model.put("selectedSpot", selectedSpot);

        return "newSpot";
    }


}

