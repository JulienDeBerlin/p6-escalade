package com.berthoud.ocp6.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class ControllerTests {

    @RequestMapping(value = "/test/autocomplete", method = RequestMethod.GET)
    public String getSpots() {







        return "testAutocomplete";
    }

}