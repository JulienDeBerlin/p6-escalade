package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceMemberLibrairy;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.Route;
import com.berthoud.ocp6.model.bean.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@SessionAttributes(value= { "user", "guidebooksForLoan" })
@Controller
public class ControllerLibrairy {

    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceMemberLibrairy serviceMemberLibrairy;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;


    @RequestMapping(value = "memberArea/librairy", method = RequestMethod.GET)
    public String goToLibrairy (ModelMap model) {
        if (model.containsAttribute("user")) {
            return "redirect:login/espaceMembre";
        }
        else {
            String message = "onlyMembers";
            model.put("message", message);
            model.put("jspAfterLogin", "redirect:login/espaceMembre");
            return "login";
        }
    }

    @RequestMapping(value = "memberArea/librairy/isbn", method = RequestMethod.POST)
    public String addBookToLibrairy (@RequestParam (value = "isbn13") String isbn13,
                                     @SessionAttribute (value = "user") Member user,
                                     @SessionAttribute (value = "guidebooksForLoan") List<Guidebook> guidebooksForLoan,
                                     ModelMap model) {

        String message;
        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);

        if (serviceMemberLibrairy.isAlredayListed(selectedGuidebook,guidebooksForLoan )) {
            message = "alreadyListed";
        } else if (guidebooksForLoan == null) {
            message = "notFound";
            } else {
                serviceMemberLibrairy.insertGuidebook(selectedGuidebook, user);
            message = "guidebookAdded";
            }

        model.put("message", message);
        return "redirect:/escalade/login/espaceMembre";
    }


    @RequestMapping(value = "memberArea/librairy/delete", method = RequestMethod.GET)
    public String removeGuidebookFromLibrairy (@RequestParam (value = "guidebookId") int IdGuidebookToBeDeleted,
                                     @SessionAttribute (value = "user") Member user,
                                     ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(IdGuidebookToBeDeleted);
        serviceMemberLibrairy.removeGuidebook(selectedGuidebook, user);
        model.put("message", "guidebookRemoved");

        return "redirect:/escalade/login/espaceMembre";

    }
}
