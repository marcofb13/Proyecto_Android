package com.example.proyectofinderado.retrofit;

import com.example.proyectofinderado.model.AddDto;
import com.example.proyectofinderado.model.AddToShow;
import com.example.proyectofinderado.model.ProvinceDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddApi {

    @POST("/adds/add")
    Call<AddDto> addAdd(@Body AddDto addDto);

    @GET("/adds/filterAdds")
    Call<List<AddDto>> getFilteredAdds(@Query("province_id") int province_id, @Query("town_id") int town_id,
                                       @Query("subject_id") int subject_id);

    @GET("/adds/allAdds")
    Call<List<AddDto>> getAllAdds();

    @GET("/adds/addsToShowByUserId")
    Call<List<AddToShow>> getAddsByUserId(@Query("user_id") int user_id);

    @DELETE("/adds/deleteById")
    Call<String>  deleteById(@Query("id") int id);
}
