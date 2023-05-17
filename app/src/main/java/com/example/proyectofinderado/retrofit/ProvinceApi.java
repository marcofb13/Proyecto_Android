package com.example.proyectofinderado.retrofit;

import com.example.proyectofinderado.model.ProvinceDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProvinceApi {

    @GET("/provinces/allProvinces")
    Call<List<ProvinceDto>> getAllProvinces();

    @GET("/provinces/byId")
    Call<ProvinceDto> provinceById(@Query("id") int id);


}
