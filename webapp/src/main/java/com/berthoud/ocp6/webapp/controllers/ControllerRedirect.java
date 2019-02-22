package com.berthoud.ocp6.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes(value = "anchor")
@Controller
public class ControllerRedirect {

    @RequestMapping (value = "/redirect", method = RequestMethod.GET)
    public String goToIndexAtAnchor(@RequestParam (value = "anchor") String anchor,  ModelMap model){
        model.put("anchor", anchor);
        return "index";
    }
}
