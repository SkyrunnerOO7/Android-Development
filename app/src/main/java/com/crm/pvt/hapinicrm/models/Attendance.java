package com.crm.pvt.hapinicrm.models;

public class Attendance {
    String IMEI,Date,Time,Logout;
    long BreakTime;

    Attendance(){

    }

    public Attendance(String IMEI, String date, String time, String logout, long breakTime) {
        this.IMEI = IMEI;
        Date = date;
        Time = time;
        Logout = logout;
        BreakTime = breakTime;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLogout() {
        return Logout;
    }

    public void setLogout(String logout) {
        Logout = logout;
    }

    public long getBreakTime() {
        return BreakTime;
    }

    public void setBreakTime(long breakTime) {
        BreakTime = breakTime;
    }
}
