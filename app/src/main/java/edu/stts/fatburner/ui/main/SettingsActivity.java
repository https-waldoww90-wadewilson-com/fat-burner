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
    private MaterialEditText etCalorie;
    private Button btnSave;
    private API mApiInterface;
    private SharedPreferences pref;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etCalorie = findViewById(R.id.et_settings_calorie);
        btnSave = findViewById(R.id.btn_settings_save);
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //untuk judul activity
        setTitle("Settings");
        mApiInterface = ApiClient.getClient().create(API.class);
        pref = getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etCalorie.getText().toString().equals("")){
                    saveCalorieGoal(Double.parseDouble(etCalorie.getText().toString()));
                }else Toast.makeText(SettingsActivity.this, "All Field must be filled!", Toast.LENGTH_SHORT).show();
            }
        });

        loadCalorieGoal();
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
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<CalorieResponse> call, Throwable t) {
                Toast.makeText(SettingsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void saveCalorieGoal(double calorie){
        //untuk dialog
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<InsertResponse> loadCall = mApiInterface.updateCalorieGoal(token,userid,new CalorieUpdateBody(calorie));
        loadCall.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
                if (response != null && !response.isError()) finish();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(SettingsActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
