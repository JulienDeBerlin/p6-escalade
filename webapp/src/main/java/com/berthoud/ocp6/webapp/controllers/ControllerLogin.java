package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLogin;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@SessionAttributes (value = {"user"})
@Controller
public class ControllerLogin {

    @Autowired
    ServiceLogin serviceLogin;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String displayloginPage(@RequestParam (value = "afterLogin") String jspAfterLogin,
                                   ModelMap model){
        model.put("jspAfterLogin",jspAfterLogin );
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkPassword(@RequestParam (value = "email") String inputEmail,
                                @RequestParam (value = "password") String inputPassword,
                                @RequestParam (value = "afterLogin") String jspAfterLogin,
                                ModelMap model) {
        String message;
        Member user = serviceLogin.findMemberByEmail(inputEmail);

        if (user==null){
            message="memberNotFound";
            model.put("message", message);
            model.put("jspAfterLogin",jspAfterLogin );
            return "login";
        }

        if (serviceLogin.checkPassword(inputPassword, user)){
            model.put ("user", user);
            return jspAfterLogin;
        }else {
            message="wrongPassword";
            model.put("message", message);
            model.put("jspAfterLogin",jspAfterLogin );
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, SessionStatus status){
        status.setComplete();
        return "index";
    }

}
