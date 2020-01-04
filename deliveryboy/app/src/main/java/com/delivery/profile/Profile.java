package com.delivery.profile;

    public class Profile {
        String id;
        String name1;
        String address;
        String aadhar;

        public Profile(){

        }

        public Profile(String id, String name1, String address, String aadhar) {
            this.id = id;
            this.name1 = name1;
            this.address = address;
            this.aadhar = aadhar;
        }

        public String getName1() {
            return name1;
        }

        public String getId() {
            return id;
        }


        public String getAddress() {
            return address;
        }

        public String getAadhar() {
            return aadhar;
        }
    }


