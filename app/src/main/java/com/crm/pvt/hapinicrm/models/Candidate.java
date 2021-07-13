package com.crm.pvt.hapinicrm.models;

public class Candidate {
    String Area,City,Mail,Experience,Phone,Name,Organization,Password;

    Candidate(){

    }

    public Candidate(String city, String contact, String email, String experience, String name, String password, String qualification, String skills) {
        City = city;
        Phone = contact;
        Mail = email;
        Experience = experience;
        Name = name;
        Password = password;
        Organization = qualification;
        Area = skills;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
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

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
