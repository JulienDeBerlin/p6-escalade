package com.berthoud.ocp6.business;

import com.berthoud.ocp6.consumer.contract.dao.MemberLibrairyDao;
import com.berthoud.ocp6.model.bean.Booking;
import com.berthoud.ocp6.model.bean.Guidebook;
import com.berthoud.ocp6.model.bean.Member;
import com.berthoud.ocp6.model.bean.MemberLibrairy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceMemberLibrairy {

    @Autowired
    MemberLibrairyDao memberLibrairy;

    @Transactional
    public MemberLibrairy insertGuidebook(Guidebook selectedGuidebook, Member user) {
        return memberLibrairy.insertGuidebook(selectedGuidebook, user);
    }

    @Transactional
    public void removeGuidebook(Guidebook selectedGuidebook, Member user) {
        memberLibrairy.removeGuidebook(selectedGuidebook, user);
    }

    /**
     * This method checks if the ID of a the selectedGuidebook is to be found among a list of guidebook objects
     *
     * @param selectedGuidebook the guidebook whose id is being checked
     * @param listGuidebook     the list of guidebook
     * @return true if the list contains a guidebook with the same id as the id of selectedGuidebook
     */
    public boolean isAlredayListed(Guidebook selectedGuidebook, List<Guidebook> listGuidebook) {

        // Transform a list of Guidebook objects into a list of Guidebook ID:
        List<Integer> listIdGuidebooks = new ArrayList<>();
        for (Iterator<Guidebook> i = listGuidebook.iterator(); i.hasNext(); ) {
            Guidebook guidebook = i.next();
            listIdGuidebooks.add(guidebook.getId());
        }

        return listIdGuidebooks.contains(selectedGuidebook.getId());
    }


    public MemberLibrairy getMemberLibrairyItem(Guidebook selectedGuidebook, Member user) {
        return memberLibrairy.findMemberLibrairy(selectedGuidebook, user);
    }

    @Transactional
    public Booking insertBooking(MemberLibrairy privateGuidebook, Booking booking) {
        return memberLibrairy.insertBooking(privateGuidebook, booking);
    }

    @Transactional
    public void removeBooking(int bookingId) {
        memberLibrairy.removeBooking(bookingId);
    }

    @Transactional
    public void updateBooking(Booking booking) {
        memberLibrairy.updateBooking(booking);
    }


    public Booking findBookingById(int bookingId) {
        return memberLibrairy.findBookingById(bookingId);
    }


    /**
     * This method checks if a private guidebook is available for booking in the period requested
     *
     * @param privateGuidebook    the guidebook whose availabilities should be checked
     * @param bookingRequestFrom  the begin of the requested booking period
     * @param bookingRequestUntil the end of the requested booking period
     * @return true is the private guidebook is available for the requested booking
     */
    public boolean periodBookingRequestAvailable(MemberLibrairy privateGuidebook, LocalDate bookingRequestFrom, LocalDate bookingRequestUntil) {
        List<Booking> bookings = privateGuidebook.getBookings();
        for (Iterator<Booking> i = bookings.iterator(); i.hasNext(); ) {
            Booking booking = i.next();
            if (bookingRequestFrom.isAfter(booking.getDateUntil()) || bookingRequestUntil.isBefore(booking.getDateFrom())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used in the process of updating a booking. It checks if the private guidebook is available for the updated
     * period of booking.
     *
     * @param privateGuidebook the guidebook whose availabilities should be checked
     * @param selectedBooking  the booking to be updated
     * @param newDateFrom      the updated begin of the requested booking period
     * @param newDateUntil     the updated  end of the requested booking period
     * @return true is the private guidebook is available for the updated requested booking
     */
    public boolean periodBookingUpdateAvailable(MemberLibrairy privateGuidebook, Booking selectedBooking, LocalDate newDateFrom, LocalDate newDateUntil) {
        List<Booking> actualBookings = privateGuidebook.getBookings();
        actualBookings.remove(selectedBooking);

        if (periodBookingRequestAvailable(privateGuidebook, newDateFrom, newDateUntil)) {
            actualBookings.add(selectedBooking);
            return true;
        } else {
            actualBookings.add(selectedBooking);
            return false;
        }
    }


    /**
     * This method takes as input a guidebook and a requested period of booking and return a list of matching private guidebooks
     * available for loan in the requested period.
     *
     * @param selectedGuidebook the guidebook that is searched among the members' private librairies.
     * @param date_from         the begin of the requested booking period
     * @param date_until        the end of the requested booking period
     * @return a list of privateGuidebooks available for the requested period.
     */
    public List<MemberLibrairy> findAvailablePrivateGuidebooks(Guidebook selectedGuidebook, LocalDate date_from, LocalDate date_until) {
        List<MemberLibrairy> listPrivateGuidebooks = memberLibrairy.findMemberLibrairyByGuidebookId(selectedGuidebook.getId());

        for (Iterator<MemberLibrairy> i = listPrivateGuidebooks.iterator(); i.hasNext(); ) {
            MemberLibrairy privateGuidebook = i.next();
            if (!periodBookingRequestAvailable(privateGuidebook, date_from, date_until)) {
                i.remove();
            }
        }
        return listPrivateGuidebooks;
    }

    /**
     * This method sorts the bookings contained in a list by ascending order of the date of the beginning of the booking.
     *
     * @param bookings the list to be sorted
     * @return the sorted list
     */
    public List<Booking> sortBooking(List<Booking> bookings) {
        if (bookings != null) {
            Collections.sort(bookings);
        }
        return bookings;
    }


}
