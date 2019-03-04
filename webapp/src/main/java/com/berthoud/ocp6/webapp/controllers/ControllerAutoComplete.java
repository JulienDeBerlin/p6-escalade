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

    @RequestMapping(value = "/get_location_list", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLocationList(@RequestParam("term") String query) {
        List<String>  locationProposals = serviceLocation.getLocationProposals(query);
        return locationProposals;
    }

}
