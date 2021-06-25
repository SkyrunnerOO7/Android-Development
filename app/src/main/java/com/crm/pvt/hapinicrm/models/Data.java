package com.crm.pvt.hapinicrm.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Timer;

public class Data  {
    private String Name,Contact,City,Email,Experience,Password,Qualification,Skills,Type,Time;

    Data(){

    }

    public Data(String time,String name, String contact, String city, String email, String experience, String password, String qualifiction, String skills,String type)
    {
        Time = time;
        Type = type;
        Name = name;
        Contact = contact;
        City = city;
        Email = email;
        Experience = experience;
        Password = password;
        Qualification = qualifiction;
        Skills = skills;
    }

    public String getType() {
        return Type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getQualifiction() {
        return Qualification;
    }

    public void setQualifiction(String qualifiction) {
        Qualification = qualifiction;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }




}
