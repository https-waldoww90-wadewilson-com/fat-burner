package edu.stts.fatburner.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.User;

public class SugarActivity extends AppCompatActivity {
    private ImageButton btnNextSugar;
    private EditText et;
    private User userBaru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sugar);
        btnNextSugar = findViewById(R.id.btnNextSugar);
        et = findViewById(R.id.editText);
        userBaru = (User) getIntent().getSerializableExtra("userBaru");
    }

    public void nextSugar(View v) {
        Intent intent = null;
        if(!et.getText().toString().equals("")){
            switch(v.getId()){
                case R.id.btnNextSugar:
                    double sugar = Double.parseDouble(et.getText().toString());
                    userBaru.setBloodsugar(sugar);
                    intent = new Intent(this,CholesterolActivity.class);
                    intent.putExtra("userBaru",userBaru);
                    break;
            }
            if (intent != null && !et.getText().equals("")) startActivity(intent);
        }
        else Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
    }
}
