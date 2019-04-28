package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EndRegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etEmail,etUsername,etPassword,etConfirm;
    private User userBaru;
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
        userBaru = (User)getIntent().getSerializableExtra("userBaru");
    }

    public void btnRegister(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btn_register:
                if(etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                    userBaru.setEmail(etEmail.getText().toString());
                    userBaru.setUsername(etUsername.getText().toString());
                    userBaru.setPassword(etPassword.getText().toString());
                    intent = new Intent(this, LoginActivity.class);

                }
                Toast.makeText(EndRegisterActivity.this,userBaru.getCholesterol() + "",Toast.LENGTH_LONG).show();
                break;
        }
        //if (intent != null) startActivity(intent);
    }
}
