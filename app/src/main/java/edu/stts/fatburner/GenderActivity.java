package edu.stts.fatburner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;

import edu.stts.fatburner.classObject.User;

public class GenderActivity extends AppCompatActivity {
    private ImageButton btnNextGender;
    private RadioButton rd1;
    private User userBaru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gender);
        btnNextGender = findViewById(R.id.btnNextGender);
        rd1 = findViewById(R.id.radioButton);
        userBaru = (User) getIntent().getSerializableExtra("userBaru");
    }

    public void nextGender(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnNextGender:
                userBaru.setGender(rd1.isChecked());
                intent = new Intent(this,WeightActivity.class);
                intent.putExtra("userBaru",userBaru);
                break;
        }
        if (intent != null) startActivity(intent);
    }
}
