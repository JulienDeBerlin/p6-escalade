package com.berthoud.ocp6.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes(value= {"user"})
public class ControllerAddContent {

    @RequestMapping(value = "addcontent/spot", method = RequestMethod.GET)
    public String addSpot (ModelMap model) {
        if (model.containsAttribute("user")) {
            return "newSpot";
        }
        else {
            String message = "onlyMembers";
            model.put("message", message);

            model.put("jspAfterLogin", "newSpot");

            return "login";
        }
    }
}

