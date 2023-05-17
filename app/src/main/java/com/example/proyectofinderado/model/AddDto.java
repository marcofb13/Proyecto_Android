package com.example.proyectofinderado.model;

public class AddDto {


    private int id;
    private String description;
    private int province_id;
    private int  town_id;
    private int subject_id;
    private int user_id;



    public AddDto() {
    }

    public AddDto(int id, String description, int province_id, int town_id, int subject_id, int user_id) {
        this.id = id;
        this.description = description;
        this.province_id = province_id;
        this.town_id = town_id;
        this.subject_id = subject_id;
        this.user_id = user_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
