package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class HeightActivity extends AppCompatActivity {
    private ImageButton btnNextHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_height);
        btnNextHeight = findViewById(R.id.btnNextHeight);
    }

    public void nextHeight(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextHeight:
                intent = new Intent(this,SugarActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
