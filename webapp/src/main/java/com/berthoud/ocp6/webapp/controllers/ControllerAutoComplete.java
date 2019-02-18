package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
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
    LocationDao locationDao;


    @RequestMapping(value = "/get_location_list",
            method = RequestMethod.GET)
//            headers="Accept=*/*")

    public @ResponseBody
    List<String> getCountryList(@RequestParam("term") String query) {
        List<String>  locationProposals = locationDao.getLocationProposals(query);
        return locationProposals;
    }

}
