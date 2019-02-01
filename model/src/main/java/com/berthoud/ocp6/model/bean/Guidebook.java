package com.berthoud.ocp6.model.bean;

public class Guidebook {
    private int id;
    private int isbn13;
    private String name;
    private short yearPublication;
    private String publisher;
    private String language;
    private String summary;
    private String firstnameAuthor;
    private String SurnameAuthor;

    public Guidebook(int id, int isbn13, String name, short yearPublication, String publisher,
                     String language, String summary, String firstnameAuthor, String surnameAuthor) {
        this.id = id;
        this.isbn13 = isbn13;
        this.name = name;
        this.yearPublication = yearPublication;
        this.publisher = publisher;
        this.language = language;
        this.summary = summary;
        this.firstnameAuthor = firstnameAuthor;
        SurnameAuthor = surnameAuthor;
    }

    public Guidebook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(int isbn13) {
        this.isbn13 = isbn13;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(short yearPublication) {
        this.yearPublication = yearPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFirstnameAuthor() {
        return firstnameAuthor;
    }

    public void setFirstnameAuthor(String firstnameAuthor) {
        this.firstnameAuthor = firstnameAuthor;
    }

    public String getSurnameAuthor() {
        return SurnameAuthor;
    }

    public void setSurnameAuthor(String surnameAuthor) {
        SurnameAuthor = surnameAuthor;
    }

    @Override
    public String toString() {
        return "Guidebook{" +
                "id=" + id +
                ", isbn13=" + isbn13 +
                ", name='" + name + '\'' +
                ", yearPublication=" + yearPublication +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                ", summary='" + summary + '\'' +
                ", firstnameAuthor='" + firstnameAuthor + '\'' +
                ", SurnameAuthor='" + SurnameAuthor + '\'' +
                '}';
    }
}
