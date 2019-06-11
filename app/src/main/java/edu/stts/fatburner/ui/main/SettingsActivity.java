package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.CalorieUpdateBody;
import edu.stts.fatburner.data.network.response.CalorieResponse;
import edu.stts.fatburner.data.network.response.InsertResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    private MaterialEditText etCalorie,etWeight,etHeight,etChol,etSugar;
    private Button btnSave;
    private API mApiInterface;
    private SharedPreferences pref;
    private SweetAlertDialog pDialog;
    private RadioButton rb1,rb2,rb3,rb4,rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etCalorie = findViewById(R.id.et_settings_calorie);
        etWeight = findViewById(R.id.et_settings_weight);
        etHeight = findViewById(R.id.et_settings_height);
        etChol = findViewById(R.id.et_settings_chol);
        etSugar = findViewById(R.id.et_settings_blood);
        btnSave = findViewById(R.id.btn_settings_save);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //untuk judul activity
        setTitle("Settings");
        mApiInterface = ApiClient.getClient().create(API.class);
        pref = getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllowed()){
                    int temp = 1;
                    if(rb1.isChecked()) temp = 1;
                    else if(rb2.isChecked()) temp = 2;
                    else if(rb3.isChecked()) temp = 3;
                    else if(rb4.isChecked()) temp = 4;
                    else if(rb5.isChecked()) temp = 5;
                    saveCalorieGoal(Double.parseDouble(etCalorie.getText().toString()),
                            Double.parseDouble(etWeight.getText().toString()),
                            Double.parseDouble(etHeight.getText().toString()),
                            Integer.parseInt(etSugar.getText().toString()+""),
                            Integer.parseInt(etChol.getText().toString()+""),
                            temp
                        );
                }
            }
        });

        loadCalorieGoal();
    }

    private boolean isAllowed(){
        if(etCalorie.getText().toString().equals("")||etWeight.getText().toString().equals("")||
                etHeight.getText().toString().equals("")||etSugar.getText().toString().equals("")
                ||etChol.getText().toString().equals("")){
            Toast.makeText(SettingsActivity.this, "All Field must be filled!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCalorieGoal(){
        //untuk dialog
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<CalorieResponse> loadCall = mApiInterface.getCalorie(token,userid);
        loadCall.enqueue(new Callback<CalorieResponse>() {
            @Override
            public void onResponse(Call<CalorieResponse> call, Response<CalorieResponse> res) {
                CalorieResponse response = res.body();
                etCalorie.setText((int)response.getCalorie()+"");
                etWeight.setText(response.getWeight()+"");
                etHeight.setText(response.getHeight()+"");
                etChol.setText(response.getCholesterol()+"");
                etSugar.setText(response.getBloodsugar()+"");
                if(response.getGoal()==1) rb1.setChecked(true);
                else if(response.getGoal()==2) rb2.setChecked(true);
                else if(response.getGoal()==3) rb3.setChecked(true);
                else if(response.getGoal()==4) rb4.setChecked(true);
                else if(response.getGoal()==5) rb5.setChecked(true);
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<CalorieResponse> call, Throwable t) {
                Toast.makeText(SettingsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void saveCalorieGoal(double calorie,double weight, double height, int blood,int chol,int goal){
        //untuk dialog
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<InsertResponse> loadCall = mApiInterface.updateCalorieGoal(token,userid,new CalorieUpdateBody(calorie,weight,height,blood,chol,goal));
        loadCall.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
                if (response != null && !response.isError()){
                    setGoalPref(goal);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(SettingsActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setGoalPref(int goal){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("goal",String.valueOf(goal));
        editor.apply();
    }
}
