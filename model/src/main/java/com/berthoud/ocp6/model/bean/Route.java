package com.berthoud.ocp6.model.bean;

import java.util.Objects;

public class Route {

    private int id;
    private String name;
    private byte nbPitch;
    private byte indexPitch;
    private String rating;
    private boolean bolted;
    private Spot spot;


    public Route(int id, String name, byte nbPitch, byte indexPitch,
                 String rating, boolean bolted, Spot spot) {
        this.id = id;
        this.name = name;
        this.nbPitch = nbPitch;
        this.indexPitch = indexPitch;
        this.rating = rating;
        this.bolted = bolted;
        this.spot = spot;
    }

    public Route() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getNbPitch() {
        return nbPitch;
    }

    public void setNbPitch(byte nbPitch) {
        this.nbPitch = nbPitch;
    }

    public byte getIndexPitch() {
        return indexPitch;
    }

    public void setIndexPitch(byte indexPitch) {
        this.indexPitch = indexPitch;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isBolted() {
        return bolted;
    }

    public void setBolted(boolean bolted) {
        this.bolted = bolted;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbPitch=" + nbPitch +
                ", indexPitch=" + indexPitch +
                ", rating='" + rating + '\'' +
                ", bolted=" + bolted +
                ", spot=" + spot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id &&
                nbPitch == route.nbPitch &&
                indexPitch == route.indexPitch &&
                bolted == route.bolted &&
                Objects.equals(name, route.name) &&
                Objects.equals(rating, route.rating) &&
                Objects.equals(spot, route.spot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nbPitch, indexPitch, rating, bolted, spot);
    }
}
