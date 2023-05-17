package com.example.proyectofinderado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinderado.model.UserDto;
import com.example.proyectofinderado.retrofit.RetrofitService;
import com.example.proyectofinderado.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ImageView userImage, logoImage, logoImageExtLeft, logoImageExtRight;
    EditText emailEdt, pwdEdt;
    TextView txtvRegister;
    Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userImage = findViewById(R.id.loginact_img_user);
        logoImage = findViewById(R.id.loginact_img_logo);
        logoImageExtLeft = findViewById(R.id.loginact_img_leftLogo);
        logoImageExtRight = findViewById(R.id.loginact_img_rightLogo);
        emailEdt =  findViewById(R.id.loginact_edt_email);
        pwdEdt = findViewById(R.id.loginact_edt_pwd);
        loginBtn = findViewById(R.id.loginact_btn_login);

        txtvRegister = findViewById(R.id.loginact_txtv_registro);
        txtvRegister.setMovementMethod(LinkMovementMethod.getInstance());



        userImage.getLayoutParams().height = emailEdt.getHeight();
        loginBtn.setBackgroundColor(getResources().getColor(R.color.negroGrisaceo));
        logoImageExtLeft.getLayoutParams().height = logoImage.getHeight();
        logoImageExtRight.getLayoutParams().height = logoImage.getHeight();

    }

    @Override
    protected void onStart() {
        super.onStart();

        SpannableString registerString = new SpannableString("¿No tienes cuenta? Registrate");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
        ClickableSpan registerSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);

            }
        };
        registerString.setSpan(registerSpan, 0, registerString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerString.setSpan(colorSpan, 0, registerString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtvRegister.setText(registerString);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitService rfs = new RetrofitService();
                UserApi userApi = rfs.getRetrofit().create(UserApi.class);
                String email = emailEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                userApi.login(email.trim(),pwd.trim()).enqueue(new Callback<UserDto>() {
                    @Override
                    public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                        UserDto loggedUser = response.body();
                        if(loggedUser.getEmail()==null){
                            Toast.makeText(LoginActivity.this, "No hay usuario con este email",
                                    Toast.LENGTH_SHORT).show();
                        }else if(loggedUser.getPwd()==null){
                            Toast.makeText(LoginActivity.this, "Nombre de usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Usuario aceptado",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
                            i.putExtra("id",loggedUser.getId());
                            i.putExtra("name",loggedUser.getName());
                            i.putExtra("email",loggedUser.getEmail());
                            i.putExtra("phone",loggedUser.getPhoneNumber());

                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDto> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Fallo al tratar de loggear", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}