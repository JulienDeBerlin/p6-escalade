package com.berthoud.ocp6.model.bean;

public class Route {

    private int id;
    private String name;
    private byte nbPitch;
    private byte indexPitch;
    private String rating;
    private boolean bolted;
    private int spotId;


    public Route(int id, String name, byte nbPitch, byte indexPitch,
                 String rating, boolean bolted, int spotId) {
        this.id = id;
        this.name = name;
        this.nbPitch = nbPitch;
        this.indexPitch = indexPitch;
        this.rating = rating;
        this.bolted = bolted;
        this.spotId = spotId;
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

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
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
                ", spotId=" + spotId +
                '}';
    }
}
