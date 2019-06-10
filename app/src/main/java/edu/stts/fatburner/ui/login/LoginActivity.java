package edu.stts.fatburner.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.ui.main.MainActivity;
import edu.stts.fatburner.R;
import edu.stts.fatburner.ui.register.RegisterActivity;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.LoginBody;
import edu.stts.fatburner.data.network.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvEmail;
    private TextView tvPassword;
    private API mApiInterface;
    private SharedPreferences pref;
    private SweetAlertDialog pLoadingDialog,pErrorDialog;

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
        //Untuk simpan id user yg login(kyk simpan ke session)
        pref = getApplicationContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        if(pref.getInt("userID",-1)!=-1){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
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

    private void doLogin(final String email, String password){
        //untuk loading dialog
        pLoadingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pLoadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pLoadingDialog.setTitleText("Loading");
        pLoadingDialog.setCancelable(false);
        pLoadingDialog.show();

        //untuk error dialog
        pErrorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pErrorDialog.setTitle("Oops..");
        pErrorDialog.setTitleText("Invalid email or password");

        Call<LoginResponse> loginCall = mApiInterface.login(new LoginBody(email,password));
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                pLoadingDialog.dismissWithAnimation();
                LoginResponse response = res.body();
                if(!response.isError()){
                    saveIdToken(Integer.parseInt(response.getMessage().getUserid()),response.getMessage().getToken(),response.getMessage().getWeight(),email,response.getMessage().getName(),response.getMessage().getGoal());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pLoadingDialog.dismissWithAnimation();
                pErrorDialog.show();
            }
        });
    }

    private void saveIdToken(int id,String token,float weight,String email,String name,String goal){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userID",id);
        editor.putString("token","Bearer "+token);
        editor.putFloat("weight",weight);
        editor.putString("email",email);
        editor.putString("name",name);
        editor.putString("goal",goal);
        editor.apply();
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
