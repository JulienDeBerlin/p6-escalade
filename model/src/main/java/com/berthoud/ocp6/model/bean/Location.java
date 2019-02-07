package com.berthoud.ocp6.model.bean;

import java.util.List;

public class Location {

    private int id;
    private String region;
    private String departementName;
    private int departementId;
    private String cityName;
    private String zipCode;
    private List<Spot> spots;


    public Location(int id, String region, String departementName, int departementId, String cityName, String zipCode, List<Spot> spots) {
        this.id = id;
        this.region = region;
        this.departementName = departementName;
        this.departementId = departementId;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.spots = spots;
    }

    public Location() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartementName() {
        return departementName;
    }

    public void setDepartementName(String departementName) {
        this.departementName = departementName;
    }

    public int getDepartementId() {
        return departementId;
    }

    public void setDepartementId(int departementId) {
        this.departementId = departementId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", departementName='" + departementName + '\'' +
                ", departementId=" + departementId +
                ", cityName='" + cityName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", spots=" + spots +
                '}';
    }
}


