package com.berthoud.ocp6.webapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ControllerSpots {

    private static final Logger logger = LogManager.getLogger();
//
//    @RequestMapping(value = "/spots", method = RequestMethod.GET)
//    public String getSpots() {
//        return "index";
//    }

    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public String getResultSpots() {
        return "spots";
    }

}
