package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.stts.fatburner.network.API;
import edu.stts.fatburner.network.ApiClient;
import edu.stts.fatburner.network.body.LoginBody;
import edu.stts.fatburner.network.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvEmail;
    private TextView tvPassword;
    private API mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvEmail = findViewById(R.id.etUsername);
        tvPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        mApiInterface = ApiClient.getClient().create(API.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login : {
                doLogin(tvEmail.getText().toString(),tvPassword.getText().toString());
                break;
            }
        }
    }

    private void doLogin(String email,String password){
        Call<LoginResponse> loginCall = mApiInterface.login(new LoginBody(email,password));
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                LoginResponse response = res.body();
                Toast.makeText(LoginActivity.this,response.getMessage(), Toast.LENGTH_LONG).show();
                if(!response.isError()) startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClickRegister(View v){
        Intent intent = null;
        switch(v.getId()){
            case R.id.tvRegister:
                intent = new Intent(this,RegisterActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
