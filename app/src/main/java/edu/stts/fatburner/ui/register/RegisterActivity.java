package edu.stts.fatburner.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.User;

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
        User userBaru = new User();
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextWelcome:
                intent = new Intent(this,GoalActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
