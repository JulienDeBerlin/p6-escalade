package com.berthoud.ocp6.model.bean;

public class CommentGuidebook {

    private int id;
    private int memberId;
    private int guidebookId;
    private String comment;

    public CommentGuidebook(int id, int memberId, int guidebookId, String comment) {
        this.id = id;
        this.memberId = memberId;
        this.guidebookId = guidebookId;
        this.comment = comment;
    }

    public CommentGuidebook() {
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
        return "CommentGuidebook{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", guidebookId=" + guidebookId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
