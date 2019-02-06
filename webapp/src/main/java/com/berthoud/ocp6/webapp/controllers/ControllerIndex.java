package com.berthoud.ocp6.webapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerIndex {

    private static final Logger logger = LogManager.getLogger();


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

}
