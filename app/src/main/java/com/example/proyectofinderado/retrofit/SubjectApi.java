package com.example.proyectofinderado.retrofit;

import com.example.proyectofinderado.model.ProvinceDto;
import com.example.proyectofinderado.model.SubjectDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SubjectApi {

    @GET("/subjects/allSubjects")
    Call<List<SubjectDto>> getAllSubjects();
}
