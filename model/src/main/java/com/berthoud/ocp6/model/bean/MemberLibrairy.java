package com.berthoud.ocp6.model.bean;

import java.util.Objects;

public class MemberLibrairy {

    private int id;
    private Member member;
    private Guidebook guidebook;

    public MemberLibrairy(int id, Member member, Guidebook guidebook) {
        this.id = id;
        this.member = member;
        this.guidebook = guidebook;
    }

    public MemberLibrairy() {
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

    @Override
    public String toString() {
        return "MemberLibrairy{" +
                "id=" + id +
                ", member=" + member +
                ", guidebook=" + guidebook +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberLibrairy that = (MemberLibrairy) o;
        return id == that.id &&
                Objects.equals(member, that.member) &&
                Objects.equals(guidebook, that.guidebook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, guidebook);
    }
}
