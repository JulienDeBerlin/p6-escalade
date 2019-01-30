package com.berthoud.ocp6.webapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

    private static final Logger logger = LogManager.getLogger("HelloController");

    @RequestMapping(value = "/hello")
    public String sayHello(){
        // use the viewresolver (in appconfig-mvc.xml) to find the jsp:
        logger.info("This Is An Info Log Entry ......!");
        logger.error("This Is An Error Log Entry ......!");
        return "hello";
    }
}
