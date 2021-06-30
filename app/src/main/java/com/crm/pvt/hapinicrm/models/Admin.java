package com.crm.pvt.hapinicrm.models;

import android.net.Uri;

public class Admin {
    private String Passcode;
    private String Password;
    private String Name;
    private String Email;
    private String City;
    private String Phone;
    private String Image;

    Admin(){

    }

    public Admin(String passcode, String password, String name, String email, String city, String phone, String image) {
        Passcode = passcode;
        Password = password;
        Name = name;
        Email = email;
        City = city;
        Phone = phone;
        Image = image;
    }

    public String getPasscode() {
        return Passcode;
    }

    public void setPasscode(String passcode) {
        Passcode = passcode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
