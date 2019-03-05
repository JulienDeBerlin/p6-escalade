package com.berthoud.ocp6.model.bean;

import java.util.List;
import java.util.Objects;

public class MemberLibrairy {

    private int id;
    private Member member;
    private Guidebook guidebook;
    private List<Booking> bookings;

    public MemberLibrairy() {
    }

    public MemberLibrairy(int id, Member member, Guidebook guidebook, List<Booking> bookings) {
        this.id = id;
        this.member = member;
        this.guidebook = guidebook;
        this.bookings = bookings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Guidebook getGuidebook() {
        return guidebook;
    }

    public void setGuidebook(Guidebook guidebook) {
        this.guidebook = guidebook;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "MemberLibrairy{" +
                "id=" + id +
                ", member=" + member +
                ", guidebook=" + guidebook +
                ", bookings=" + bookings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberLibrairy that = (MemberLibrairy) o;
        return id == that.id &&
                Objects.equals(member, that.member) &&
                Objects.equals(guidebook, that.guidebook) &&
                Objects.equals(bookings, that.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, guidebook, bookings);
    }
}