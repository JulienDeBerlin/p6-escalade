package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceMemberLibrairy;
import com.berthoud.ocp6.model.bean.Booking;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import static com.berthoud.ocp6.business.Utils.convertStringIntoDate;

import java.time.LocalDate;
import java.util.List;


@SessionAttributes(value= { "user", "guidebooksForLoan", "privateGuidebook", "selectedGuidebook"})
@Controller
public class ControllerLibrairy {

    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceMemberLibrairy serviceMemberLibrairy;



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


    @RequestMapping(value = "memberArea/librairy/bookings", method = RequestMethod.GET)
    public String goToBookings(@ModelAttribute(value = "guidebookId") int GuidebookId,
                               @SessionAttribute(value = "user") Member user,
                               ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(GuidebookId);
        MemberLibrairy privateGuidebook = serviceMemberLibrairy.getMemberLibrairy(selectedGuidebook, user);

        model.put("privateGuidebook", privateGuidebook);
        model.put("selectedGuidebook", selectedGuidebook);


        return "guidebookBookings";
    }


    @RequestMapping(value = "memberArea/librairy/bookings", method = RequestMethod.POST)
    public String insertBooking(@RequestParam(value = "booked_by") String booked_by,
                               @RequestParam(value = "date_from") String date_from,
                               @RequestParam(value = "date_until") String date_until,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "phone") String phone,
                               @SessionAttribute(value = "user") Member user,
                                @SessionAttribute(value = "privateGuidebook") MemberLibrairy privateGuidebook,
                               ModelMap model) {

        Booking newBooking = new Booking();
        newBooking.setBookedBy(booked_by);
        newBooking.setDateFrom(convertStringIntoDate(date_from));
        newBooking.setDateUntil(convertStringIntoDate(date_until));
        newBooking.setEmail(email);
        newBooking.setPhone(phone);
        newBooking.setMemberLibrairyGuidebookId(privateGuidebook.getGuidebook().getId());
        newBooking.setMemberLibrairyMemberId(user.getId());

        serviceMemberLibrairy.insertBooking(privateGuidebook,newBooking);

        model.put("guidebookId", privateGuidebook.getGuidebook().getId());

        return "redirect:";
    }


}
