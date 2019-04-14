package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class GenderActivity extends AppCompatActivity {
    private ImageButton btnNextGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gender);
        btnNextGender = findViewById(R.id.btnNextGender);
    }

    public void nextGender(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextGender:
                intent = new Intent(this,WeightActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
