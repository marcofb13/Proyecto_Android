package com.example.proyectofinderado.retrofit;

import com.example.proyectofinderado.model.ProvinceDto;
import com.example.proyectofinderado.model.TownDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TownApi {

    @GET("/towns/allTowns")
    Call<List<TownDto>> getAllTowns();

    @GET("/towns/byProvinceId")
    Call<List<TownDto>> getTownsByProvinceId(@Query("id") int id);
}
