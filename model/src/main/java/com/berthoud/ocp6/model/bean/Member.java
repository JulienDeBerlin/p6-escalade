package com.berthoud.ocp6.model.bean;

import java.util.Date;

public class Member {

    private int id;
    private String firstName;
    private String surname;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private Date dateMembership;

    public Member() {
    }

    public Member(int id, String firstName, String surname, String nickname, String password, String email, String phone, Date dateMembership) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dateMembership = dateMembership;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getDateMembership() {
        return dateMembership;
    }

    public void setDateMembership(Date dateMembership) {
        this.dateMembership = dateMembership;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateMembership=" + dateMembership +
                '}';
    }
}
