package com.crm.pvt.hapinicrm.models;

public class CustomerB2B {

    String Name,Organization,Mail,Password,City,Phone;

    CustomerB2B(){

    }

    public CustomerB2B(String name, String organization, String mail, String password, String city, String phone) {
        Name = name;
        Organization = organization;
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

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String organization) {
        Organization = organization;
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
