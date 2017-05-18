package com.cgtta.cgtta.classes;

/**
 * Created by shubh on 5/12/2017.
 */

public class AssociationDetails {
    String name;
    String position;
    String title;
    String email;
    String number;
    String address;
    String profile_url;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AssociationDetails(String title, String name, String position, String profile_url, String address, String number, String email) {
        this.name = name;
        this.position = position;
        this.title = title;
        this.profile_url = profile_url;
        this.address = address;
        this.email = email;
        this.number = number;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
