package com.example.proyectofinderado.model;

public class TownDto {

    private int id;
    private String name;
    private int province_id;

    public TownDto(){

    }

    public TownDto(int id, String name, int province_id){
        this.id=id;
        this.name=name;
        this.province_id=province_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }


}
