package com.example.pung.codemonkeys;

/**
 * Created by prath on 2/20/2018.
 */

public class Search {
    private int id;
    private String name;
    private String address;

    public Search(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

}
