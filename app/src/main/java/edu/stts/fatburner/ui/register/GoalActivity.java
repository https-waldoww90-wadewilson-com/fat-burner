package edu.stts.fatburner.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.User;

public class GoalActivity extends AppCompatActivity {
    private ImageButton btnNextGoal;
    RadioButton rd1, rd2, rd3, rd4, rd5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goal);
        btnNextGoal = findViewById(R.id.btnNextGoal);
        rd1 = findViewById(R.id.radioButton);
        rd2 = findViewById(R.id.radioButton2);
        rd3 = findViewById(R.id.radioButton3);
        rd4 = findViewById(R.id.radioButton4);
        rd5 = findViewById(R.id.radioButton5);
    }

    public void nextGoal(View v) {
        Intent intent = null;
        User userBaru = new User();
        if(rd1.isChecked()) userBaru.setGoal(1);
        else if(rd2.isChecked()) userBaru.setGoal(2);
        else if(rd3.isChecked()) userBaru.setGoal(3);
        else if(rd4.isChecked()) userBaru.setGoal(4);
        else if(rd5.isChecked()) userBaru.setGoal(5);
        switch(v.getId()){
            case R.id.btnNextGoal:
                intent = new Intent(this,GenderActivity.class);
                intent.putExtra("userBaru", userBaru);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
