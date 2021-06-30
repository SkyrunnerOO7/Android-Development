package com.crm.pvt.hapinicrm.models;

public class CustomerB2C {
    String Name,Area,Mail,Password,City,Phone;

    CustomerB2C(){

    }

    public CustomerB2C(String name, String area, String mail, String password, String city, String phone) {
        Name = name;
        Area = area;
        Mail = mail;
        Password = password;
        City = city;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
