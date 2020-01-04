package com.example.deliveryboy;



public class profile {
    String id;
    String name1;
    String address;
    String lisence;
    String mobile;

    public profile(){

    }

    public profile(String id, String name1, String address, String lisence, String mobile) {
        this.id = id;
        this.name1 = name1;
        this.address = address;
        this.lisence = lisence;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public String getName1() {
        return name1;
    }

    public String getAddress() {
        return address;
    }

    public String getLisence() {
        return lisence;
    }

    public String getMobile() {
        return mobile;
    }
}
