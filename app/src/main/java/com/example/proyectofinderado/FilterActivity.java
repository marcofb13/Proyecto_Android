package com.example.proyectofinderado;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinderado.model.ProvinceDto;
import com.example.proyectofinderado.model.SubjectDto;
import com.example.proyectofinderado.model.TownDto;
import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.ProvinceApi;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.SubjectApi;
import com.example.proyectofinderado.retrofit.TownApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    TextView txtvLocality, txtvProvince, txtvSubjects;
    Button btnFilter;
    Spinner spnLocality, spnProvince, spnSbj1;
    List<ProvinceDto> provinceList;
    List<TownDto> townList;
    List<SubjectDto> subjectList;
    String strProvince, strTown, strSubject;
    Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        txtvLocality=findViewById(R.id.dialogact_txtv_locality);
        txtvProvince=findViewById(R.id.dialogact_txtv_province);
        txtvSubjects=findViewById(R.id.dialogact_txtv_subjects);

        btnFilter = findViewById(R.id.dialog_filter);
        btnFilter.setBackgroundColor(getResources().getColor(R.color.negroGrisaceo));

        spnLocality = findViewById(R.id.dialogact_spinner_locality);
        spnProvince = findViewById(R.id.dialogact_spinner_province);
        spnSbj1 = findViewById(R.id.dialogact_spinner_subject1);

        extras = getIntent().getExtras();


        loadProvinces();
        loadSubjects();
    }

    @Override
    protected void onStart() {
        super.onStart();

        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                strProvince = selectedItem;
                for (ProvinceDto pDto : provinceList) {
                    if (pDto.getName().equals(selectedItem)) {
                        loadTowns(pDto);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnLocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                strTown = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnSbj1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                strSubject = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddsListActivity.class);
                i.putExtra("id",extras.getInt("id"));
                i.putExtra("name",extras.getString("name"));
                i.putExtra("email",extras.getString("email"));
                i.putExtra("phone",extras.getString("phone"));
                i.putExtra("province",strProvince.toString());
                i.putExtra("town",strTown.toString());
                i.putExtra("subject",strSubject.toString());
                for(ProvinceDto p : provinceList){
                    if(p.getName().equals(strProvince)){
                        i.putExtra("province_id",p.getId());
                    }
                }

                for(TownDto t: townList){
                    if(t.getName().equals(strTown)){
                        i.putExtra("town_id",t.getId());
                    }
                }

                for(SubjectDto s: subjectList){
                    if(s.getName().equals(strSubject)){
                        i.putExtra("subject_id",s.getId());
                    }
                }

                startActivity(i);
            }
        });
    }

    private void loadSubjects() {
        RetrofitService rfs = new RetrofitService();
        SubjectApi subjectApi = rfs.getRetrofit().create(SubjectApi.class);
        subjectApi.getAllSubjects().enqueue(new Callback<List<SubjectDto>>() {
            @Override
            public void onResponse(Call<List<SubjectDto>> call, Response<List<SubjectDto>> response) {
                populateSubjectSpinner(response.body());
            }

            @Override
            public void onFailure(Call<List<SubjectDto>> call, Throwable t) {

            }
        });
    }

    private void populateSubjectSpinner(List<SubjectDto> body) {

        List<String> subjectNames = new ArrayList<>();

        for(SubjectDto s : body){
            subjectNames.add(s.getName());
        }

        subjectList = body;

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectNames);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSbj1.setAdapter(subjectAdapter);

    }


    private void loadTowns(ProvinceDto pDto) {
        RetrofitService rfs = new RetrofitService();
        TownApi townApi = rfs.getRetrofit().create(TownApi.class);
        townApi.getTownsByProvinceId(pDto.getId()).enqueue(new Callback<List<TownDto>>() {
            @Override
            public void onResponse(Call<List<TownDto>> call, Response<List<TownDto>> response) {
                populateTownSpinner(response.body());
            }

            @Override
            public void onFailure(Call<List<TownDto>> call, Throwable t) {

            }
        });
    }


    void loadProvinces() {
        RetrofitService rfs = new RetrofitService();
        ProvinceApi provinceApi = rfs.getRetrofit().create(ProvinceApi.class);
        provinceApi.getAllProvinces().enqueue(new Callback<List<ProvinceDto>>() {
            @Override
            public void onResponse(Call<List<ProvinceDto>> call, Response<List<ProvinceDto>> response) {
                if(response.isSuccessful()){

                    populateProvinceSpinner(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProvinceDto>> call, Throwable t) {
                Toast.makeText(FilterActivity.this, "Fallo al cargar" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateProvinceSpinner(List<ProvinceDto> body) {
        List<String> provinceNames = new ArrayList<>();

        for(ProvinceDto p : body){
            provinceNames.add(p.getName());
        }

        provinceList = body;

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceNames);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProvince.setAdapter(provinceAdapter);
    }

    private void populateTownSpinner(List<TownDto> body) {

        List<String> townNames = new ArrayList<>();

        for(TownDto t: body){
            townNames.add(t.getName());
        }

        townList = body;

        ArrayAdapter<String> townAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, townNames);
        townAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLocality.setAdapter(townAdapter);
    }

}
