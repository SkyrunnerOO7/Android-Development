package com.crm.pvt.hapinicrm.models;

public class Feedback {
    private String Problem,Status,Remark,Name,Phone,City;

    public Feedback() {
    }

    public Feedback(String problem, String status, String remark, String name, String phone,String city) {
        Problem = problem;
        City = city;
        Status = status;
        Remark = remark;
        Name = name;
        Phone = phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
