package com.MNbeerapp.pung.codemonkeys;

/**
 * Created by prath on 2/20/2018.
 */

public class Search {
    private int id;
    private String name;
    private String address;
    private String beerType;
    private String beerName;
    private String phone;
    private String email;
    private String website;
    private String ABV;


    public Search(int id, String name, String address, String beerType, String beerName, String phone, String email, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.beerName = beerName;
        this.beerType = beerType;
        this.phone = phone;
        this.email = email;
        this.website = website;

    }

    public Search(int id, String beerName, String beerType, String ABV) {
        this.id = id;
        this.beerName = beerName;
        this.beerType = beerType;
        this.ABV = ABV;

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public String getBeerType() {
        return beerType;
    }
    public String getBeerName() {
        return beerName;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getWebsite() {
        return website;
    }
    public String getABV() {
        return ABV;
    }

}
