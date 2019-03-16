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


@SessionAttributes(value = {"user", "guidebooksForLoan", "privateGuidebook",
        "selectedGuidebook", "selectedBooking"})
@Controller
public class ControllerLibrairy {

    @Autowired
    ServiceGuidebook serviceGuidebook;
    @Autowired
    ServiceMemberLibrairy serviceMemberLibrairy;

    private static final Logger logger = LogManager.getLogger();


    /**
     * This controller-method is used when the user wants to propose a guidebook for loan. It checks if the user is logged in,
     * if yes, the request is redirected to {@link ControllerLogin#goToMemberArea(ModelMap, Member, String)}
     * if no, the login-page is displayed
     *
     * @param model //
     * @return login.jsp or redirection to member area
     */
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

    /**
     * This controller-method is used when a member registers a guidebook for loan.
     *
     * @param isbn13            the isbn number of the guidebook
     * @param user              the member
     * @param guidebooksForLoan the list of the guidebooks the user already offer for loan
     * @param model             //
     * @return redirection to {@link ControllerLogin#goToMemberArea(ModelMap, Member, String)}
     */
    @RequestMapping(value = "memberArea/librairy/isbn", method = RequestMethod.POST)
    public String addBookToLibrairy(@RequestParam(value = "isbn13") String isbn13,
                                    @SessionAttribute(value = "user") Member user,
                                    @SessionAttribute(value = "guidebooksForLoan") List<Guidebook> guidebooksForLoan,
                                    ModelMap model) {

        String message;
        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyIsbn(isbn13);

        if (selectedGuidebook == null) {
            message = "notFound";
        } else if (serviceMemberLibrairy.isAlredayListed(selectedGuidebook, guidebooksForLoan)) {
            message = "alreadyListed";
        } else {
            serviceMemberLibrairy.insertGuidebook(selectedGuidebook, user);
            message = "guidebookAdded";
        }

        model.put("message", message);
        return "redirect:/escalade/login/espaceMembre";
    }

    /**
     * This controller-method removes a guidebook from the user's librairy when the user doesn't want to offer it for loan
     * anymore.
     *
     * @param IdGuidebookToBeDeleted the id of the guidebook that should be removed from the librairy
     * @param user                   the member
     * @param model                  //
     * @return redirection to {@link ControllerLogin#goToMemberArea(ModelMap, Member, String)}
     */
    @RequestMapping(value = "memberArea/librairy/delete", method = RequestMethod.GET)
    public String removeGuidebookFromLibrairy(@RequestParam(value = "guidebookId") int IdGuidebookToBeDeleted,
                                              @SessionAttribute(value = "user") Member user,
                                              ModelMap model) {

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(IdGuidebookToBeDeleted);
        serviceMemberLibrairy.removeGuidebook(selectedGuidebook, user);
        model.put("message", "guidebookRemoved");

        return "redirect:/escalade/login/espaceMembre";

    }

    /**
     * With this controller-method the user has access to the details of the bookings for a selected guidebook of his librairy.
     *
     * @param GuidebookId the id of the selected guidebook
     * @param user        the member
     * @param model       //
     * @return guidebookBookings.jsp, the page dedicated to the booking management
     */
    @RequestMapping(value = "memberArea/librairy/goToBookings", method = RequestMethod.GET)
    public String goToBookings(@RequestParam(value = "guidebookId") int GuidebookId,
                               @SessionAttribute(value = "user") Member user,
                               ModelMap model) {

        logger.debug("Entering goToBookings");

        Guidebook selectedGuidebook = serviceGuidebook.findGuidebookbyId(GuidebookId);

        logger.debug("entering getMemberLibrairy ");

        MemberLibrairy privateGuidebook = serviceMemberLibrairy.getMemberLibrairyItem(selectedGuidebook, user);
        Collections.sort(privateGuidebook.getBookings());

        model.put("privateGuidebook", privateGuidebook);
        model.put("selectedGuidebook", selectedGuidebook);

        logger.debug("Finishing  goToBookings");

        return "guidebookBookings";
    }


    /**
     * With this controller-method the user can register a new booking for a selected guidebook of his librairy.
     *
     * @param booked_by        name of the member who want to borrow the guidebook
     * @param date_from        beginning of the loan
     * @param date_until       end of the loan
     * @param email            //
     * @param phone            //
     * @param user             the member who makes the loan
     * @param privateGuidebook the guidebook the member offer for loan
     * @param model            //
     * @return guidebookBookings.jsp, the page dedicated to the booking management if the booking is not possible. In case of success,
     * the request is redirected to {@link #goToBookings(int, Member, ModelMap)}, in order for the updated booking to be displayed.
     */
    @RequestMapping(value = "memberArea/librairy/bookings")
    public String insertBooking(@ModelAttribute(value = "booked_by") String booked_by,
                                @ModelAttribute(value = "date_from") String date_from,
                                @ModelAttribute(value = "date_until") String date_until,
                                @ModelAttribute(value = "email") String email,
                                @ModelAttribute(value = "phone") String phone,
                                @SessionAttribute(value = "user") Member user,
                                @SessionAttribute(value = "privateGuidebook") MemberLibrairy privateGuidebook,
                                ModelMap model) {

        LocalDate dateFrom = LocalDate.parse(date_from);
        LocalDate dateUntil = LocalDate.parse(date_until);
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


    /**
     * With the controller-method a member can delete a booking for one of his guidebooks. It first removes the booking
     * from the private guidebook and then reload the updated private guidebook
     *
     * @param bookingId         the id of the booking to be deleted
     * @param selectedGuidebook //
     * @param user              //
     * @param model             //
     * @return guidebookBookings.jsp, the page dedicated to the booking management
     */
    @RequestMapping(value = "memberArea/librairy/bookings/delete", method = RequestMethod.GET)
    public String removeBookingFromList(@RequestParam(value = "bookingId") int bookingId,
                                        @ModelAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                                        @ModelAttribute(value = "user") Member user,
                                        ModelMap model) {

        serviceMemberLibrairy.removeBooking(bookingId);
        model.put("message", "bookingRemoved");

        MemberLibrairy privateGuidebook = serviceMemberLibrairy.getMemberLibrairyItem(selectedGuidebook, user);
        model.put("privateGuidebook", privateGuidebook);


        return "guidebookBookings";

    }

    /**
     * This controller-method is responsible for the update of one guidebook-booking.
     *
     * @param bookingId the id of the booking to be updated
     * @param model     //
     * @return
     */
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

    /**
     * This controller-method is used for updating a booking. The period of loan of the updated booking is checked for validity.
     * If it's ok, the booking is updated and the request redirected to {@link #goToBookings(int, Member, ModelMap)},
     * in order for the updated booking to be displayed
     *
     * @param booked_by        name of the member who want to borrow the guidebook
     * @param date_from        beginning of the loan
     * @param date_until       end of the loan
     * @param email            //
     * @param phone            //
     * @param selectedBooking  the booking to be updated
     * @param privateGuidebook the guidebook the member offer for loan
     * @param model            //
     * @return If success: redirected to {@link #goToBookings(int, Member, ModelMap)}, in order for the
     * updated booking to be displayed, otherwise display of an alert on the same guidebookBookings.jsp
     */

    @RequestMapping(value = "memberArea/librairy/bookings/update", method = RequestMethod.POST)

    public String updateBooking(@RequestParam(value = "booked_by") String booked_by,
                                @RequestParam(value = "date_from") String date_from,
                                @RequestParam(value = "date_until") String date_until,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "phone") String phone,
                                @SessionAttribute(value = "selectedBooking") Booking selectedBooking,
                                @SessionAttribute(value = "privateGuidebook") MemberLibrairy privateGuidebook,
                                ModelMap model) {

        LocalDate dateFrom = LocalDate.parse(date_from);
        LocalDate dateUntil = LocalDate.parse(date_until);
        boolean periodAvailable = serviceMemberLibrairy.periodBookingUpdateAvailable(privateGuidebook, selectedBooking, dateFrom, dateUntil);

        if (dateUntil.isBefore(dateFrom)) {
            model.put("message", "dateWrong");
            return "guidebookBookings";
        } else if (periodAvailable) {
            Booking updatedBooking = new Booking();
            updatedBooking.setBookedBy(booked_by);
            updatedBooking.setDateFrom(dateFrom);
            updatedBooking.setDateUntil(dateUntil);
            updatedBooking.setEmail(email);
            updatedBooking.setPhone(phone);
            updatedBooking.setId(selectedBooking.getId());
            serviceMemberLibrairy.updateBooking(updatedBooking);
            model.put("guidebookId", privateGuidebook.getGuidebook().getId());
            return "redirect:/escalade/memberArea/librairy/goToBookings";
        } else {
            model.put("message", "periodNotAvailable");
            return "guidebookBookings";
        }
    }


    /**
     * This controller-method takes as input the id of the guidebook the user would like to book.
     * It displays a booking form for this guidebook.
     *
     * @param guidebookId the id of the guidebook the user would like to book
     * @param model       //
     * @return
     */
    @RequestMapping(value = "memberArea/librairy/loan", method = RequestMethod.GET)
    public String displayBookingForm(@RequestParam(value = "guidebookId") int guidebookId,
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


    /**
     * @param date_from
     * @param date_until
     * @param selectedGuidebook
     * @param model
     * @return
     */
    @RequestMapping(value = "memberArea/librairy/loan/checkDates", method = RequestMethod.POST)
    public String displayBookingForm(@RequestParam(value = "date_from") String date_from,
                                     @RequestParam(value = "date_until") String date_until,
                                     @SessionAttribute(value = "selectedGuidebook") Guidebook selectedGuidebook,
                                     ModelMap model) {

        LocalDate dateFrom = LocalDate.parse(date_from);
        LocalDate dateUntil = LocalDate.parse(date_until);

        if (dateUntil.isBefore(dateFrom)) {
            model.put("message", "dateWrong");
            return "loan";
        } else {
            List<MemberLibrairy> privateGuidebooks =
                    serviceMemberLibrairy.findAvailablePrivateGuidebooks(selectedGuidebook, dateFrom, dateUntil);
            model.put("privateGuidebooks", privateGuidebooks);
        }

        return "loan";
    }
}



