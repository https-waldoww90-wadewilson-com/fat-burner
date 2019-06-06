package edu.stts.fatburner.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.jaredrummler.materialspinner.MaterialSpinner;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.User;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.response.RegisterResponse;
import edu.stts.fatburner.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndRegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etEmail,etName,etPassword,etConfirm;
    private MaterialSpinner spinner;
    private User userBaru;
    private API mApiInterface;
    private String plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_register);
        btnRegister = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        spinner = findViewById(R.id.spinner);
        mApiInterface = ApiClient.getClient().create(API.class);
        userBaru = (User)getIntent().getSerializableExtra("userBaru");

        spinner.setItems("Free", "Basic", "Silver", "Gold");
        plan = "Free";
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position==0) plan = "Free";
                else if(position == 1) plan = "Basic";
                else if(position == 2) plan = "Silver";
                else if(position == 3) plan = "Gold";
            }
        });
    }

    public void btnRegister(View v) {
        if(!etEmail.getText().toString().isEmpty()&&!etName.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&!etConfirm.getText().toString().isEmpty()){
            boolean allow = true;
            if(!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                allow = false;
                Toast.makeText(EndRegisterActivity.this,"Password & Confirm Password must be same",Toast.LENGTH_SHORT).show();
            }
            if(!checkEmail(etEmail.getText().toString())) allow = false;
            if(allow){
                userBaru.setEmail(etEmail.getText().toString());
                userBaru.setName(etName.getText().toString());
                userBaru.setPassword(etPassword.getText().toString());
                doRegister(userBaru);
            }
        }else Toast.makeText(EndRegisterActivity.this,"All field must be filled",Toast.LENGTH_SHORT).show();
    }

    private boolean checkEmail(String email){
        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Toast.makeText(EndRegisterActivity.this,"Email not valid",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
