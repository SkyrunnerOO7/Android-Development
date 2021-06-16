package com.crm.pvt.hapinicrm.models;

public class Employee {
    private String IMEI,Password,mail;

    public Employee() {

    }

    public Employee(String IMEI, String password, String mail) {
        this.IMEI = IMEI;
        Password = password;
        this.mail = mail;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
