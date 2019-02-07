package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.SpotManager;
import com.berthoud.ocp6.model.bean.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ControllerSpots {

    @Autowired
    private SpotManager spotManager;

    private static final Logger logger = LogManager.getLogger();
//
//    @RequestMapping(value = "/spots", method = RequestMethod.GET)
//    public String getSpots() {
//        return "index";
//    }

    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public String getResultSpots(@RequestParam (value = "lieu") String lieu, ModelMap model) {
        model.put("listSpots", spotManager.lookForDetailledSpotsBasedOnLocation(lieu));
        return "spots";
    }

}
