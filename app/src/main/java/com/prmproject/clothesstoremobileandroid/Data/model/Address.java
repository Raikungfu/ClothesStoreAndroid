package com.prmproject.clothesstoremobileandroid.Data.model;

public class Address {
    private int id;

    private String fullName;
    private String address;
    private String city;
    private String state;
    private String phone;
    private String country;

    public Address(int id, String fullName, String address, String city, String state, String phone, String country) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.country = country;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }
}
