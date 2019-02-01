package com.berthoud.ocp6.model.bean;

public class CommentSpot {

    private int id;
    private int memberId;
    private int spotId;
    private String comment;

    public CommentSpot(int id, int memberId, int spotId, String comment) {
        this.id = id;
        this.memberId = memberId;
        this.spotId = spotId;
        this.comment = comment;
    }

    public CommentSpot() {
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

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentSpot{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", spotId=" + spotId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
