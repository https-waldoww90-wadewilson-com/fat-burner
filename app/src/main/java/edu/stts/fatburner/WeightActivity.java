package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class WeightActivity extends AppCompatActivity {
    private ImageButton btnNextWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weight);
        btnNextWeight = findViewById(R.id.btnNextWeight);
    }

    public void nextWeight(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextWeight:
                intent = new Intent(this,HeightActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
