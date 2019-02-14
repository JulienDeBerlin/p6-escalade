package com.berthoud.ocp6.model.bean;

import java.util.List;
import java.util.Objects;

public class Spot {

    private int id;
    private String nameSpot;
    private String nameArea;
    private Location location;
    private List<Route> routes;
    private List<Guidebook> guidebooks;
    private List<SpotComment> comments;

    public Spot(int id, String nameSpot, String nameArea, Location location, List<Route> routes, List<Guidebook> guidebooks, List<SpotComment> comments) {
        this.id = id;
        this.nameSpot = nameSpot;
        this.nameArea = nameArea;
        this.location = location;
        this.routes = routes;
        this.guidebooks = guidebooks;
        this.comments = comments;
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

    public List<SpotComment> getComments() {
        return comments;
    }

    public void setComments(List<SpotComment> comments) {
        this.comments = comments;
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
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot = (Spot) o;
        return id == spot.id &&
                Objects.equals(nameSpot, spot.nameSpot) &&
                Objects.equals(nameArea, spot.nameArea) &&
                Objects.equals(location, spot.location) &&
                Objects.equals(routes, spot.routes) &&
                Objects.equals(guidebooks, spot.guidebooks) &&
                Objects.equals(comments, spot.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameSpot, nameArea, location, routes, guidebooks, comments);
    }
}
