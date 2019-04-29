package edu.stts.fatburner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.stts.fatburner.classObject.User;
import edu.stts.fatburner.network.API;
import edu.stts.fatburner.network.ApiClient;
import edu.stts.fatburner.network.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndRegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etEmail,etUsername,etPassword,etConfirm;
    private User userBaru;
    private API mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_register);
        btnRegister = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.etUsername);
        etUsername = findViewById(R.id.etUsername2);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        mApiInterface = ApiClient.getClient().create(API.class);
        userBaru = (User)getIntent().getSerializableExtra("userBaru");
    }

    public void btnRegister(View v) {
        if(etPassword.getText().toString().equals(etConfirm.getText().toString())) {
            userBaru.setEmail(etEmail.getText().toString());
            userBaru.setUsername(etUsername.getText().toString());
            userBaru.setPassword(etPassword.getText().toString());
            doRegister(userBaru);
        }
    }

    private void doRegister(User user){
        Call<RegisterResponse> regCall = mApiInterface.register(user);
        regCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> res) {
                RegisterResponse response = res.body();
                if (response != null && !response.isError()) {
                    Toast.makeText(EndRegisterActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EndRegisterActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(EndRegisterActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
