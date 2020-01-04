package com.delivery;

public class Profile {
    String id;
    String name;
    String address;
    String license;
    String mobile;

    public  Profile(){

    }

    public Profile(String id, String name, String address, String license, String mobile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.license = license;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLicense() {
        return license;
    }

    public String getMobile() {
        return mobile;
    }
}

