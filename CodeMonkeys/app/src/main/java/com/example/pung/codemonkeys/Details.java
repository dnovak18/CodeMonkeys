package com.example.pung.codemonkeys;

/**
 * Created by Jeffrey on 3/24/2018.
 */

public class Details {

    private int id;
    private String phone;
    private String name;
    private String address;
    private String type;
    private String beerName;
    private String abv;
    private String email;
    private String website;

    public Details(int id, String phone, String name, String address, String type, String beerName, String abv, String email, String website){
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.phone = phone;
        this.beerName = beerName;
        this.abv = abv;
        this.email = email;
        this.website = website;
    }

    public String getName(){

        return name;
    }

    public void setName(String name){

        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public String getBeerType(){
        return type;
    }

    public String getBeerName(){
        return beerName;
    }

    public String getBeerABV(){
        return abv;
    }

    public String getEmail(){
        return email;
    }

    public String getWebsite(){
        return website;
    }

    public String getPhone(){
        return phone;
    }
}
