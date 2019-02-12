package com.berthoud.ocp6.model.bean;

import java.util.List;

public class Guidebook {
    private int id;
    private String isbn13;
    private String name;
    private short yearPublication;
    private String publisher;
    private String language;
    private String summary;
    private String firstnameAuthor;
    private String SurnameAuthor;
    private List<CommentGuidebook> commentsGuidebook;
    private List <MemberLibrairy> memberLibrairies;


    public Guidebook(int id, String isbn13, String name, short yearPublication, String publisher, String language, String summary, String firstnameAuthor,
                     String surnameAuthor, List<CommentGuidebook> commentsGuidebook, List<MemberLibrairy> memberLibrairies) {
        this.id = id;
        this.isbn13 = isbn13;
        this.name = name;
        this.yearPublication = yearPublication;
        this.publisher = publisher;
        this.language = language;
        this.summary = summary;
        this.firstnameAuthor = firstnameAuthor;
        SurnameAuthor = surnameAuthor;
        this.commentsGuidebook = commentsGuidebook;
        this.memberLibrairies = memberLibrairies;
    }

    public Guidebook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
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

    public List<CommentGuidebook> getCommentsGuidebook() {
        return commentsGuidebook;
    }

    public void setCommentsGuidebook(List<CommentGuidebook> commentsGuidebook) {
        this.commentsGuidebook = commentsGuidebook;
    }

    public List<MemberLibrairy> getMemberLibrairies() {
        return memberLibrairies;
    }

    public void setMemberLibrairies(List<MemberLibrairy> memberLibrairies) {
        this.memberLibrairies = memberLibrairies;
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
                ", commentsGuidebook=" + commentsGuidebook +
                ", memberLibrairies=" + memberLibrairies +
                '}';
    }
}
