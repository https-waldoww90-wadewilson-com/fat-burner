package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvRegister = findViewById(R.id.tvRegister);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btn_login : {
                i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                break;
            }
        }
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
