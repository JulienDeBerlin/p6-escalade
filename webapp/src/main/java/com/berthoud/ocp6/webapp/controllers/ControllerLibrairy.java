package com.berthoud.ocp6.webapp.controllers;

import com.berthoud.ocp6.business.ServiceGuidebook;
import com.berthoud.ocp6.business.ServiceMemberLibrairy;
import com.berthoud.ocp6.model.bean.Booking;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.berthoud.ocp6.business.Utils.convertStringIntoDate;


@SessionAttributes(value = {"user", "guidebooksForLoan", "privateGuidebook",
        "selectedGuidebook", "selectedBooking"})
@Controller
public class ControllerLibrairy {

    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceMemberLibrairy serviceMemberLibrairy;

    private static final Logger logger = LogManager.getLogger();


    @RequestMapping(value = "memberArea/librairy", method = RequestMethod.GET)
    public String goToLibrairy(ModelMap model) {
        if (model.containsAttribute("user")) {
            return "redirect:/escalade/login/espaceMembre";
        } else {
            String message = "onlyMembers";
            model.put("message", message);
            model.put("jspAfterLogin", "redirect:/escalade/login/espaceMembre");
            return "login";
        }
    }

    @RequestMapping(value = "memberArea/librairy/isbn", method = RequestMethod.POST)
    public String addBookToLibrairy(@RequestParam(value = "isbn13") String isbn13,
                                    @SessionAttribute(value = "user") Member user,
                                    @SessionAttribute(value = "guidebooksForLoan") List<Guidebook> guidebooksForLoan,
                                    ModelMap model) {

        String message;
        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);

        if (serviceMemberLibrairy.isAlredayListed(selectedGuidebook, guidebooksForLoan)) {
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
    public String removeGuidebookFromLibrairy(@RequestParam(value = "guidebookId") int IdGuidebookToBeDeleted,
                                              @SessionAttribute(value = "user") Member user,
                                              ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(IdGuidebookToBeDeleted);
        serviceMemberLibrairy.removeGuidebook(selectedGuidebook, user);
        model.put("message", "guidebookRemoved");

        return "redirect:/escalade/login/espaceMembre";

    }


    @RequestMapping(value = "memberArea/librairy/goToBookings", method = RequestMethod.GET)
    public String goToBookings(@RequestParam(value = "guidebookId") int GuidebookId,
                               @SessionAttribute(value = "user") Member user,
                               ModelMap model) {

        logger.debug("Entering goToBookings");

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(GuidebookId);

        logger.debug("entering getMemberLibrairy ");

        MemberLibrairy privateGuidebook = serviceMemberLibrairy.getMemberLibrairy(selectedGuidebook, user);
        Collections.sort(privateGuidebook.getBookings());

        model.put("privateGuidebook", privateGuidebook);
        model.put("selectedGuidebook", selectedGuidebook);

        logger.debug("Finisching  goToBookings");

        return "guidebookBookings";
    }


    @RequestMapping(value = "memberArea/librairy/bookings")
    public String insertBooking(@ModelAttribute(value = "booked_by") String booked_by,
                                @ModelAttribute(value = "date_from") String date_from,
                                @ModelAttribute(value = "date_until") String date_until,
                                @ModelAttribute(value = "email") String email,
                                @ModelAttribute(value = "phone") String phone,
                                @SessionAttribute(value = "user") Member user,
                                @SessionAttribute(value = "privateGuidebook") MemberLibrairy privateGuidebook,
                                ModelMap model) {

        LocalDate dateFrom = convertStringIntoDate(date_from);
        LocalDate dateUntil = convertStringIntoDate(date_until);
        boolean periodAvailable = serviceMemberLibrairy.periodBookingRequestAvailable(privateGuidebook, dateFrom, dateUntil);

        if (dateUntil.isBefore(dateFrom)) {
            model.put("message", "dateWrong");
            return "guidebookBookings";

        }

        if (periodAvailable) {

            Booking newBooking = new Booking();
            newBooking.setBookedBy(booked_by);
            newBooking.setDateFrom(dateFrom);
            newBooking.setDateUntil(dateUntil);
            newBooking.setEmail(email);
            newBooking.setPhone(phone);
            newBooking.setMemberLibrairyGuidebookId(privateGuidebook.getGuidebook().getId());
            newBooking.setMemberLibrairyMemberId(user.getId());

            serviceMemberLibrairy.insertBooking(privateGuidebook, newBooking);

            model.put("guidebookId", privateGuidebook.getGuidebook().getId());

            return "redirect:goToBookings";

        } else {
            model.put("message", "periodNotAvailable");
            return "guidebookBookings";
        }
    }

    @RequestMapping(value = "memberArea/librairy/bookings/delete", method = RequestMethod.GET)
    public String removeBookingFromList(@RequestParam(value = "bookingId") int bookingId,
                                        @ModelAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                                        @ModelAttribute(value = "user") Member user,
                                        ModelMap model) {

        serviceMemberLibrairy.removeBooking(bookingId);
        model.put("message", "bookingRemoved");

        MemberLibrairy privateGuidebook = serviceMemberLibrairy.getMemberLibrairy(selectedGuidebook, user);
        model.put("privateGuidebook", privateGuidebook);

        return "guidebookBookings";

    }


    @RequestMapping(value = "memberArea/librairy/bookings/update", method = RequestMethod.GET)
    public String updateBooking(@RequestParam(value = "bookingId") int bookingId,
                                ModelMap model) {

        Booking selectedbooking = serviceMemberLibrairy.findBookingById(bookingId);

        model.put("action", "modify");
        model.put("bookedBy", selectedbooking.getBookedBy());
        model.put("dateFrom", selectedbooking.getDateFrom());
        model.put("dateUntil", selectedbooking.getDateUntil());
        model.put("email", selectedbooking.getEmail());
        model.put("Phone", selectedbooking.getPhone());

        model.put("selectedBooking", selectedbooking);

        return "guidebookBookings";

    }


    @RequestMapping(value = "memberArea/librairy/bookings/update", method = RequestMethod.POST)
    public String updateBooking(@RequestParam(value = "booked_by") String booked_by,
                                @RequestParam(value = "date_from") String date_from,
                                @RequestParam(value = "date_until") String date_until,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "phone") String phone,
                                @SessionAttribute(value = "selectedBooking") Booking selectedBooking,
                                ModelMap model) {

        serviceMemberLibrairy.removeBooking(selectedBooking.getId());
        model.remove("selectedBooking", selectedBooking);

        model.put("booked_by", booked_by);
        model.put("date_from", date_from);
        model.put("date_until", date_until);
        model.put("email", email);
        model.put("phone", phone);

        return "redirect:/escalade/memberArea/librairy/bookings";
    }


    @RequestMapping(value = "memberArea/librairy/loan", method = RequestMethod.GET)
    public String goToLoan(@RequestParam(value = "guidebookId") int guidebookId,
                           ModelMap model) {
        if (model.containsAttribute("user")) {
            Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(guidebookId);
            model.put("selectedGuidebook", selectedGuidebook);
            return "loan";
        } else {
            String message = "onlyMembers";
            model.put("message", message);
            model.put("jspAfterLogin", "redirect:/escalade/memberArea/librairy/loan?guidebookId=" + guidebookId);
            return "login";
        }
    }

    @RequestMapping(value = "memberArea/librairy/loan/checkDates", method = RequestMethod.POST)
    public String goToLoan(@RequestParam(value = "date_from") String date_from,
                           @RequestParam(value = "date_until") String date_until,
                           @SessionAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                           ModelMap model) {

        LocalDate dateFrom = convertStringIntoDate(date_from);
        LocalDate dateUntil = convertStringIntoDate(date_until);

        if (dateUntil.isBefore(dateFrom)) {
            model.put("message", "dateWrong");
            return "loan";
        } else{
           List<MemberLibrairy> privateGuidebooks =
                   serviceMemberLibrairy.findAvailablePrivateGuidebooks(selectedGuidebook,dateFrom, dateUntil);
            model.put("privateGuidebooks", privateGuidebooks);
        }

        return "loan";
    }





}


