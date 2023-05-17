package com.example.proyectofinderado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.AddApi;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView txtvName, txtvEmail, txtvPwd, txtvPwdConf, txtvPhone;
    EditText edtName, edtEmail, edtPwd, edtPwdConf, edtPhone;
    Button btnRegister;
    ImageView logoImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtvName = findViewById(R.id.registeract_txtv_name);
        txtvEmail = findViewById(R.id.registeract_txtv_email);
        txtvPwd = findViewById(R.id.registeract_txtv_pwd);
        txtvPwdConf = findViewById(R.id.registeract_txtv_pwdConf);
        txtvPhone = findViewById(R.id.registeract_txtv_phone);

        edtName = findViewById(R.id.registeract_edt_name);
        edtEmail = findViewById(R.id.registeract_edt_email);
        edtPwd = findViewById(R.id.registeract_edt_pwd);
        edtPwdConf = findViewById(R.id.registeract_edt_pwdConf);
        edtPhone = findViewById(R.id.registeract_edt_phone);

        btnRegister = findViewById(R.id.registeract_btn_enviar);
        btnRegister.setBackgroundColor(getResources().getColor(R.color.naranja1));

        logoImg = findViewById(R.id.registerLogo_img);


    }

    @Override
    protected void onStart() {
        super.onStart();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDto userToSave = new UserDto();
                userToSave.setEmail(edtEmail.getText().toString());
                userToSave.setPwd(edtPwd.getText().toString());
                userToSave.setName(edtName.getText().toString());
                userToSave.setPhoneNumber(edtPhone.getText().toString());

                if(edtPwd.getText().toString().equals(edtPwdConf.getText().toString())){
                    if(userToSave.getEmail().isEmpty()||
                    userToSave.getName().isEmpty()||
                    userToSave.getPhoneNumber().isEmpty()||
                    userToSave.getPwd().isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Rellene todos los campos",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        RetrofitService rfs = new RetrofitService();
                        UserApi userApi = rfs.getRetrofit().create(UserApi.class);
                        userApi.addUser(userToSave).enqueue(new Callback<UserDto>() {
                            @Override
                            public void onResponse(Call<UserDto> call, Response<UserDto> response) {

                                Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
                                i.putExtra("id",userToSave.getId());
                                i.putExtra("name",userToSave.getName().toString());
                                i.putExtra("email",userToSave.getEmail().toString());
                                i.putExtra("phone",userToSave.getPhoneNumber().toString());
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<UserDto> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Fallo al crear usuario",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "Confirmar contraseña incorrecto",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
