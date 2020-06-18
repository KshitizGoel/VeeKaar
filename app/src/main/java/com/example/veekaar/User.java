package com.example.veekaar;

public class User {

    public String carNumber , contact , flat , name;

    public User(String carNumber, String contact, String flat, String name) {
        this.carNumber = carNumber;
        this.contact = contact;
        this.flat = flat;
        this.name = name;
    }

    public User() {
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
