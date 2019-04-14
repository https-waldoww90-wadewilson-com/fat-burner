package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class SugarActivity extends AppCompatActivity {
    private ImageButton btnNextSugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sugar);
        btnNextSugar = findViewById(R.id.btnNextSugar);
    }

    public void nextSugar(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextSugar:
                intent = new Intent(this,CholesterolActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
