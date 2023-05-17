package com.example.proyectofinderado.model;

public class AddToShow {

    private String name;
    private String description;
    private String province;
    private String town;
    private String subject;
    private String phone;

    public AddToShow() {
    }

    public AddToShow(String name, String description, String province, String town, String subject, String phone) {
        this.name = name;
        this.description = description;
        this.province = province;
        this.town = town;
        this.subject = subject;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
