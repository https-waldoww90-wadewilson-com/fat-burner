package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class EndRegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_register);
        btnRegister = findViewById(R.id.btn_register);
    }

    public void btnRegister(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btn_register:
                intent = new Intent(this,LoginActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
