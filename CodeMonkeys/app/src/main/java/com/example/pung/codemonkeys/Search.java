package com.example.pung.codemonkeys;

/**
 * Created by prath on 2/20/2018.
 */

public class Search {
    private int id;
    private String name;
    private String address;
    private String beerType;
    private String beerName;

    public Search(int id, String name, String address, String beerType, String beerName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.beerName = beerName;
        this.beerType = beerType;
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

}
