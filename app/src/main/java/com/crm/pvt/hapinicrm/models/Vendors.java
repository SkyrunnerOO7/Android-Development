package com.crm.pvt.hapinicrm.models;

public class Vendors {

    String Name,Organization,Mail,Password,City,Experience,Area,Phone,Service,SubService;

    Vendors(){


    }

    public Vendors(String name, String organization, String mail, String password, String city, String experience, String area, String phone, String service, String subService) {
        Name = name;
        Organization = organization;
        Mail = mail;
        Password = password;
        City = city;
        Experience = experience;
        Area = area;
        Phone = phone;
        Service = service;
        SubService = subService;
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

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getSubService() {
        return SubService;
    }

    public void setSubService(String subService) {
        SubService = subService;
    }
}
