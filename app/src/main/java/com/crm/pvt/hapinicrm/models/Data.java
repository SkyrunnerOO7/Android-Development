package com.crm.pvt.hapinicrm.models;

public class Data {
    private String Name,City,Time,Contact;

    Data(){

    }

    public Data(String name, String city,String time,String contact) {
        Name = name;
        City = city;
        Contact = contact;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
