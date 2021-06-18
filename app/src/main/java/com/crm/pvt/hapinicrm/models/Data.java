package com.crm.pvt.hapinicrm.models;

public class Data {
    private String Name,Number,City;

    Data(){

    }

    public Data(String name, String number, String city) {
        Name = name;
        Number = number;
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
