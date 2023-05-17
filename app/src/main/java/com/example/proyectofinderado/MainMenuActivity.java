package com.example.proyectofinderado;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinderado.model.ProvinceDto;
import com.example.proyectofinderado.model.SubjectDto;
import com.example.proyectofinderado.model.TownDto;
import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.ProvinceApi;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.SubjectApi;
import com.example.proyectofinderado.retrofit.TownApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {

    TextView txtvWelcome, txtvName, txtvInterested;
    FloatingActionButton searchBtn;
    UserDto loggedUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtvWelcome = findViewById(R.id.mainmenu_txtv_welcome);
        txtvName = findViewById(R.id.mainmenu_txtv_name);
        txtvInterested = findViewById(R.id.mainmenu_txtv_interested);

        searchBtn = findViewById(R.id.floatingActionButton);

        Bundle extras = getIntent().getExtras();
        loggedUser = new UserDto(extras.getInt("id"),extras.getString("name"),
                extras.getString("email"),null,extras.getString("phone"));



    }

    @Override
    protected void onStart() {
        super.onStart();

        txtvName.setText(loggedUser.getName().toString());

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                i.putExtra("id", loggedUser.getId());
                i.putExtra("name", loggedUser.getName().toString());
                i.putExtra("email", loggedUser.getEmail().toString());
                i.putExtra("phone", loggedUser.getPhoneNumber().toString());
                startActivity(i);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.menu_createAdd:
                intent = new Intent(getApplicationContext(), CreateAddActivity.class);
                intent.putExtra("id",loggedUser.getId());
                intent.putExtra("name",loggedUser.getName().toString());
                intent.putExtra("email",loggedUser.getEmail().toString());
                intent.putExtra("phone",loggedUser.getPhoneNumber().toString());
                startActivity(intent);

                break;
            case R.id.menu_profile:
                intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("id",loggedUser.getId());
                intent.putExtra("name",loggedUser.getName().toString());
                intent.putExtra("email",loggedUser.getEmail().toString());
                intent.putExtra("phone",loggedUser.getPhoneNumber().toString());
                startActivity(intent);


                break;

            case R.id.menu_exit:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();

                break;

        }
        return super.onOptionsItemSelected(item);

    }

}
