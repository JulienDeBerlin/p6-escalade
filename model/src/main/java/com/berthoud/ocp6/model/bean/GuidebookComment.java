package com.berthoud.ocp6.model.bean;

import java.util.Objects;

public class GuidebookComment {

    private int id;
    private int memberId;
    private int guidebookId;
    private String comment;

    public GuidebookComment(int id, int memberId, int guidebookId, String comment) {
        this.id = id;
        this.memberId = memberId;
        this.guidebookId = guidebookId;
        this.comment = comment;
    }

    public GuidebookComment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getGuidebookId() {
        return guidebookId;
    }

    public void setGuidebookId(int guidebookId) {
        this.guidebookId = guidebookId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "GuidebookComment{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", guidebookId=" + guidebookId +
                ", comment='" + comment + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuidebookComment that = (GuidebookComment) o;
        return id == that.id &&
                memberId == that.memberId &&
                guidebookId == that.guidebookId &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, guidebookId, comment);
    }
}
