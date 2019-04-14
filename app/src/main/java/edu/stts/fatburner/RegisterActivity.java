package edu.stts.fatburner;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton btnNextWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        btnNextWelcome = findViewById(R.id.btnNextWelcome);
    }

    public void nextWelcome(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextWelcome:
                intent = new Intent(this,GoalActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
