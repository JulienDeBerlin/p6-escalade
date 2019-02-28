package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceLogin;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;


@SessionAttributes (value = {"user", "guidebooksForLoan"})
@Controller
public class ControllerLogin {

    @Autowired
    ServiceLogin serviceLogin;

    @Autowired
    ServiceGuidebook serviceGuidebook;


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

    @RequestMapping(value = "/login/espaceMembre", method = RequestMethod.GET)
    public String goToMemberArea(ModelMap model, @SessionAttribute (value = "user") Member user) {
        List<Guidebook> guidebooksForLoan = serviceGuidebook.getGuidebooksForLoan(user);
        model.put("guidebooksForLoan", guidebooksForLoan);
        return "espaceMembre";
    }



    @RequestMapping(value = "/login/resetPassword", method = RequestMethod.GET)
    public String displayResetPassword(ModelMap model) {
        model.put("action", "resetPassword");
        return "espaceMembre";
    }

    @RequestMapping(value = "/login/resetPassword", method = RequestMethod.POST)
    public String resetPassword(
            @RequestParam (value = "password1") String password1,
            @RequestParam (value = "password2") String password2,
            ModelMap model) {

        if (password1.equals(password2)){
            model.put("message", "ok");

//        method changePassword still to be written

        } else{
            model.put("action", "resetPassword");
            model.put("message", "password2different");
        }

        return "espaceMembre";
    }




}
