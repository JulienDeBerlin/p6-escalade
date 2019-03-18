package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ControllerAutoComplete {

    @Autowired
    ServiceLocation serviceLocation;

    /**
     * This method is used for the 2 autocompletion fields in the home page. It takes as input the query parameter and looks for matching locations
     * in the DB.
     *
     * @param query this is the input text entered by the user in the autocomplete field
     * @return JSON with the locations matching with the query.
     */
    @RequestMapping(value = "/get_location_list", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLocationList(@RequestParam("term") String query) {
        List<String> locationProposals = serviceLocation.getLocationProposals(query);
        return locationProposals;
    }


    /**
     * This method is used for the autocompletion field in the page dedicated to the update of referenced spots.
     * It takes as input the query parameter and looks for matching locations in the DB.
     * The difference with the method {@link #getLocationList(String)} is that it looks only for matching cities without consideration of
     * region or departement.
     *
     * @param query this is the input text entered by the user in the autocomplete field
     * @return JSON with the locations matching with the query.
     */
    @RequestMapping(value = "/autocomplete/citiesForUpdateSpots", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCityProposalsForUpdateSpots(@RequestParam("term") String query) {
        List<String> locationProposals = serviceLocation.getCityProposalsForUpdateSpots(query);
        return locationProposals;
    }


}
