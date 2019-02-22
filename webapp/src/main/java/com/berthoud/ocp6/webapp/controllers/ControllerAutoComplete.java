package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLocation;
import com.berthoud.ocp6.consumer.contract.dao.LocationDao;
import com.berthoud.ocp6.model.bean.CityForRestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ControllerAutoComplete {

    @Autowired
    ServiceLocation serviceLocation;

    @RequestMapping(value = "/get_location_list", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getCountryList(@RequestParam("term") String query) {
        List<String>  locationProposals = serviceLocation.getLocationProposals(query);
        return locationProposals;
    }

//    @RequestMapping(value = "/get_location_API_Rest", method = RequestMethod.GET)
//    public @ResponseBody
//    List<CityForRestRequest> getCity(@RequestParam("term") String query) {
//        RestTemplate restTemplate = new RestTemplate();
//        List<CityForRestRequest> cities = restTemplate.exc
//                ("http://gturnquist-quoters.cfapps.io/api/random", CityForRestRequest.class);
//        return cities


}
