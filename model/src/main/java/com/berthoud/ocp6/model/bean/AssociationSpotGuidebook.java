package com.berthoud.ocp6.model.bean;

public class AssociationSpotGuidebook {

    private int spotId;
    private int guidebookId;

    public AssociationSpotGuidebook(int spotId, int guidebookId) {
        this.spotId = spotId;
        this.guidebookId = guidebookId;
    }

    public AssociationSpotGuidebook() {
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getGuidebookId() {
        return guidebookId;
    }

    public void setGuidebookId(int guidebookId) {
        this.guidebookId = guidebookId;
    }

    @Override
    public String toString() {
        return "AssociationSpotGuidebook{" +
                "spotId=" + spotId +
                ", guidebookId=" + guidebookId +
                '}';
    }
}
