package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceLogin;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


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
        Member member = serviceLogin.findMemberByEmail(inputEmail);

        if (member==null){
            message="memberNotFound";
            model.put("message", message);
            return "login";
        }

        if (serviceLogin.checkPassword(inputPassword, member)){
            model.put ("user", member);
            return "testAutocomplete2";
        }else {
            message="wrongPassword";
            model.put("message", message);
            return "login";
        }
    }
}
