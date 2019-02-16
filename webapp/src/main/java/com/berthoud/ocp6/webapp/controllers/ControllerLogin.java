package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLogin;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@SessionAttributes (value = {"user", "loginFrom"})
@Controller
public class ControllerLogin {

    @Autowired
    ServiceLogin serviceLogin;

//    @ModelAttribute("loginFrom")
//    public String getLoginFrom(@RequestParam(value = "loginFrom") String requestOrigin)
//    {
//        return requestOrigin;
//    }

//    @ModelAttribute("user")
//    public Member addUserToModel(Member member){
//        return member;
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String displayloginPage(){
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkPassword(@RequestParam (value = "email") String inputEmail,
                                @RequestParam (value = "password") String inputPassword,
                                ModelMap model) {
        String message;
        Member user = serviceLogin.findMemberByEmail(inputEmail);

        if (user==null){
            message="memberNotFound";
            model.put("message", message);
            return "login";
        }

        if (serviceLogin.checkPassword(inputPassword, user)){
            model.put ("user", user);
            return "index";
        }else {
            message="wrongPassword";
            model.put("message", message);
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, SessionStatus status){
        status.setComplete();
        return "index";
    }

}
