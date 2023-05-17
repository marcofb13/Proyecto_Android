package com.example.proyectofinderado;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinderado.adapter.AddAdapter;
import com.example.proyectofinderado.model.AddDto;
import com.example.proyectofinderado.model.AddToShow;
import com.example.proyectofinderado.model.ProvinceDto;
import com.example.proyectofinderado.model.SubjectDto;
import com.example.proyectofinderado.model.TownDto;
import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.AddApi;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    TextView txtvName, txtvNameReceived, txtvEmail, txtvEmailReceived,
    txtvPhone, txtvPhoneReceived,txtvAdds;
    RecyclerView userAddsRecycler;

    List<AddDto> addsList;
    List<ProvinceDto> proviceList;
    List<TownDto> townList;
    List<SubjectDto> subjectList;
    List<AddToShow> addsToShowList;


    Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtvName = findViewById(R.id.profileact_txtv_name);
        txtvNameReceived = findViewById(R.id.profileact_txtv_nameReceived);
        txtvEmail = findViewById(R.id.profileact_txtv_email);
        txtvEmailReceived = findViewById(R.id.profileact_txtv_emailReceived);
        txtvPhone = findViewById(R.id.profileact_txtv_phone);
        txtvPhoneReceived = findViewById(R.id.profileact_txtv_phoneReceived);
        txtvAdds = findViewById(R.id.profileact_txtv_adds);

        userAddsRecycler = findViewById(R.id.profileact_recycler);
        userAddsRecycler.setLayoutManager(new LinearLayoutManager(this));


        extras = getIntent().getExtras();

        RetrofitService rfs = new RetrofitService();
        AddApi addApi = rfs.getRetrofit().create(AddApi.class);
        addApi.getAddsByUserId(extras.getInt("id")).enqueue(new Callback<List<AddToShow>>() {
            @Override
            public void onResponse(Call<List<AddToShow>> call, Response<List<AddToShow>> response) {
                populateRecycler(response.body());
            }

            @Override
            public void onFailure(Call<List<AddToShow>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        txtvNameReceived.setText(extras.getString("name"));
        txtvEmailReceived.setText(extras.getString("email"));
        txtvPhoneReceived.setText(extras.getString("phone"));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_editProfile:
                Dialog dialog = new Dialog(this);

                dialog.setContentView(R.layout.dialog_profile);
                final EditText edtName, edtPhone;
                final TextView txtvInfo, txtvNameDialog, txtvPhoneDialog;
                final Button btnAceptar;

                txtvInfo = dialog.findViewById(R.id.dialog_profile_info);
                txtvNameDialog = dialog.findViewById(R.id.dialog_profile_txtvName);
                txtvPhoneDialog = dialog.findViewById(R.id.dialog_profile_txtvPhone);

                btnAceptar = dialog.findViewById(R.id.dialog_profile_btnacept);

                edtName = dialog.findViewById(R.id.dialog_profile_edtName);
                edtPhone = dialog.findViewById(R.id.dialog_profile_edtPhone);
                edtName.setText(extras.getString("name"));
                edtPhone.setText(extras.getString("phone"));

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtName.getText().toString().trim().length()!=0 && edtPhone.getText().toString().trim().length()!=0){
                            RetrofitService rfs = new RetrofitService();
                            UserApi userApi = rfs.getRetrofit().create(UserApi.class);
                            UserDto u = new UserDto();
                            u.setName(edtName.getText().toString());
                            u.setPhoneNumber(edtPhone.getText().toString());
                            userApi.updateUserById(u,extras.getInt("id")).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(UserProfileActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
                                        i.putExtra("id",extras.getInt("id"));
                                        i.putExtra("name",edtName.getText().toString());
                                        i.putExtra("email",extras.getString("email"));
                                        i.putExtra("phone",edtPhone.getText().toString());

                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    //Toast.makeText(UserProfileActivity.this, "Fallo al cambiar los datos", Toast.LENGTH_SHORT).show();
                                   // dialog.dismiss();

                                    Toast.makeText(UserProfileActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
                                    i.putExtra("id",extras.getInt("id"));
                                    i.putExtra("name",edtName.getText().toString());
                                    i.putExtra("email",extras.getString("email"));
                                    i.putExtra("phone",edtPhone.getText().toString());

                                    startActivity(i);

                                }
                            });
                        }else{
                            Toast.makeText(UserProfileActivity.this, "Por favor, rellene los campos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.show();



                break;

        }
        return super.onOptionsItemSelected(item);

    }


    private void populateRecycler(List<AddToShow> addsToShow) {

        AddAdapter addAdapter = new AddAdapter(addsToShow);
        userAddsRecycler.setAdapter(addAdapter);
    }


}
