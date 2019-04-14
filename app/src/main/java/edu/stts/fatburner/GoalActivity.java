package edu.stts.fatburner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class GoalActivity extends AppCompatActivity {
    private ImageButton btnNextGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goal);
        btnNextGoal = findViewById(R.id.btnNextGoal);
    }

    public void nextGoal(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextGoal:
                intent = new Intent(this,GenderActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
