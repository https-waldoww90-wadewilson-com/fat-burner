package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class CholesterolActivity extends AppCompatActivity {
    private ImageButton btnNextCholes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cholesterol);
        btnNextCholes = findViewById(R.id.btnNextCholes);
    }

    public void nextCholes(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextCholes:
                intent = new Intent(this,EndRegisterActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
