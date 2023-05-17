package com.example.proyectofinderado.retrofit;

import com.example.proyectofinderado.model.AddDto;
import com.example.proyectofinderado.model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/users/add")
    Call<UserDto> addUser(@Body UserDto userDto);

    @GET("/users/login")
    Call<UserDto> login(@Query("email") String email, @Query("pwd") String pwd);

    @GET("/users/byId")
    Call<UserDto> getUserById(@Query("id") int id);

    @GET("users/allUsers")
    Call<List<UserDto>> getAllUsers();

    @PUT("/users/updateUser")
    Call<String> updateUserById(@Body UserDto userDto, @Query("id") int id);
}
