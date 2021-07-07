package com.crm.pvt.hapinicrm.models;

public class Employee {

    

    private String IMEI,Password,mail,Name,City,Phone,Image,Url;


    public Employee() {

    }

    public Employee(String IMEI, String password, String mail, String name, String city, String phone, String image,String url) {
        this.IMEI = IMEI;
        Url = url;
        Password = password;
        this.mail = mail;
        Name = name;
        City = city;
        Phone = phone;
        Image = image;
    }


    /*public Employee(String IMEI, String password, String mail ) {
                this.IMEI = IMEI;
                Password = password;
                this.mail = mail;
            }*/
   /*public Employee(String IMEI, String password, String mail ) {
            this.IMEI = IMEI;
            Password = password;
            this.mail = mail;
        }*/

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getName() {
        return Name;
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

    public void setName(String name) {
        Name = name;





    }
}
