package com.berthoud.ocp6.model.bean;
import java.time.LocalDate;

public class Booking implements Comparable<Booking> {

    private int id;
    private String bookedBy;
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private String email;
    private String phone;
    private int memberLibrairyMemberId;
    private int memberLibrairyGuidebookId;

    public Booking(int id, String bookedBy, LocalDate dateFrom, LocalDate dateUntil, String email, String phone,
                   int memberLibrairyMemberId, int memberLibrairyGuidebookId) {
        this.id = id;
        this.bookedBy = bookedBy;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.email = email;
        this.phone = phone;
        this.memberLibrairyMemberId = memberLibrairyMemberId;
        this.memberLibrairyGuidebookId = memberLibrairyGuidebookId;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMemberLibrairyMemberId() {
        return memberLibrairyMemberId;
    }

    public void setMemberLibrairyMemberId(int memberLibrairyMemberId) {
        this.memberLibrairyMemberId = memberLibrairyMemberId;
    }

    public int getMemberLibrairyGuidebookId() {
        return memberLibrairyGuidebookId;
    }

    public void setMemberLibrairyGuidebookId(int memberLibrairyGuidebookId) {
        this.memberLibrairyGuidebookId = memberLibrairyGuidebookId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookedBy='" + bookedBy + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", memberLibrairyMemberId=" + memberLibrairyMemberId +
                ", memberLibrairyGuidebookId=" + memberLibrairyGuidebookId +
                '}';
    }

    @Override
    public int compareTo(Booking o) {
        return this.getDateFrom().isAfter(o.getDateFrom())?1:this.getDateFrom().isBefore(o.getDateFrom())?-1:0 ;
    }
}
