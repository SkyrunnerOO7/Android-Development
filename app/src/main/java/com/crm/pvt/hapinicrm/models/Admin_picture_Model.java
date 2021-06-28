package com.crm.pvt.hapinicrm.models;

public class Admin_picture_Model {
    String Img_url;
    String IMEI;


    public Admin_picture_Model() {
    }

    public Admin_picture_Model(String img_url, String IMEI) {
        Img_url = img_url;
        this.IMEI = IMEI;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getImg_url() {
        return Img_url;
    }

    public void setImg_url(String img_url) {
        Img_url = img_url;
    }
}


