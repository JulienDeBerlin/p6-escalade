package com.berthoud.ocp6.model.bean;

import java.util.List;

public class Spot {

    private int id;
    private String nameSpot;
    private String nameArea;
    private Location location;
    private List<Route> routes;
    private List<Guidebook> guidebooks;

    public Spot(int id, String nameSpot, String nameArea, Location location, List<Route> myRoutes, List<Guidebook> myGuidebooks ) {
        this.id = id;
        this.nameSpot = nameSpot;
        this.nameArea = nameArea;
        this.location = location;
        this.routes = myRoutes;
        this.guidebooks = myGuidebooks;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Guidebook> getGuidebooks() {
        return guidebooks;
    }

    public void setGuidebooks(List<Guidebook> guidebooks) {
        this.guidebooks = guidebooks;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", nameSpot='" + nameSpot + '\'' +
                ", nameArea='" + nameArea + '\'' +
                ", location=" + location +
                ", routes=" + routes +
                ", guidebooks=" + guidebooks +
                '}';
    }
}
