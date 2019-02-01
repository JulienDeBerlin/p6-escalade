package com.berthoud.ocp6.model.bean;

public class Spot {

    private int id;
    private String nameSpot;
    private String nameArea;
    private int locationId;

    public Spot(int id, String nameSpot, String nameArea, int locationId) {
        this.id = id;
        this.nameSpot = nameSpot;
        this.nameArea = nameArea;
        this.locationId = locationId;
    }

    public Spot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSpot() {
        return nameSpot;
    }

    public void setNameSpot(String nameSpot) {
        this.nameSpot = nameSpot;
    }

    public String getNameArea() {
        return nameArea;
    }

    public void setNameArea(String nameArea) {
        this.nameArea = nameArea;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", nameSpot='" + nameSpot + '\'' +
                ", nameArea='" + nameArea + '\'' +
                ", locationId=" + locationId +
                '}';
    }
}
