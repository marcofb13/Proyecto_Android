package com.example.proyectofinderado;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinderado.adapter.AddAdapter;
import com.example.proyectofinderado.model.AddDto;
import com.example.proyectofinderado.model.AddToShow;
import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.AddApi;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    Bundle extras;
    List<UserDto> userList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds_list);

        extras = getIntent().getExtras();


        recyclerView = findViewById(R.id.addsList_reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService rfs = new RetrofitService();
        UserApi userApi = rfs.getRetrofit().create(UserApi.class);
        userApi.getAllUsers().enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                userList = response.body();
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {

                Toast.makeText(AddsListActivity.this, "Hubo un error, vuelva a cargar", Toast.LENGTH_SHORT).show();
            }
        });

        loadAdds();
    }

    private void loadAdds() {

        RetrofitService rfs = new RetrofitService();
        AddApi addApi = rfs.getRetrofit().create(AddApi.class);
        addApi.getFilteredAdds(extras.getInt("province_id"),extras.getInt("town_id"),extras.getInt("subject_id")).enqueue(new Callback<List<AddDto>>() {
            @Override
            public void onResponse(Call<List<AddDto>> call, Response<List<AddDto>> response) {
                populateRecycler(response.body());
            }

            @Override
            public void onFailure(Call<List<AddDto>> call, Throwable t) {

                Toast.makeText(AddsListActivity.this, "Fallo al cargar anuncios", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void populateRecycler(List<AddDto> addsList){

        List<AddToShow> addsToShowList = new ArrayList<>();


        for(AddDto add: addsList){
            for(UserDto user: userList){
                if(user.getId()==add.getUser_id()){
                    AddToShow ats = new AddToShow();
                    ats.setName(user.getName());
                    ats.setDescription(add.getDescription());
                    ats.setPhone(user.getPhoneNumber());
                    //estos 3 vendrán del bundle
                    ats.setProvince(extras.getString("province"));
                    ats.setTown(extras.getString("town"));
                    ats.setSubject(extras.getString("subject"));

                    addsToShowList.add(ats);
                }
            }
        }

        AddAdapter addAdapter = new AddAdapter(addsToShowList);
        recyclerView.setAdapter(addAdapter);
    }
}
