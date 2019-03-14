package com.berthoud.ocp6.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@SessionAttributes(value = "anchor")
@Controller
public class ControllerRedirect {

    @RequestMapping (value = "/redirect", method = RequestMethod.GET)
    public String goToIndexAtAnchor(@RequestParam(value = "anchor") String anchor,
                                    @ModelAttribute(value = "alert") String alert,
                                    ModelMap model) {
        model.put("anchor", anchor);
        model.put("alert", alert);
        return "index";
    }
}
