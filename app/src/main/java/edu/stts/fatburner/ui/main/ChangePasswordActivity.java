package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.PasswordBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private MaterialEditText etPass,etConfPass;
    private Button btnSave;
    private API mApiInterface;
    private SharedPreferences pref;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //untuk judul activity
        setTitle("Change Password");

        mApiInterface = ApiClient.getClient().create(API.class);
        pref = getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        etPass = findViewById(R.id.et_pass_pass);
        etConfPass = findViewById(R.id.et_pass_confpass);
        btnSave = findViewById(R.id.btn_pass_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllowed()&&isSame()) savePassword(etPass.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isAllowed(){
        if(etPass.getText().toString().equals("")||etConfPass.getText().toString().equals("")){
            Toast.makeText(ChangePasswordActivity.this, "All field must be filled in!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isSame(){
        if(!etPass.getText().toString().equals(etConfPass.getText().toString())){
            Toast.makeText(ChangePasswordActivity.this, "Password and confirmation must be same!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void savePassword(String pass){
        //untuk dialog
        pDialog = new SweetAlertDialog(ChangePasswordActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Updating your password...");
        pDialog.setCancelable(false);
        pDialog.show();

        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<InsertResponse> loadCall = mApiInterface.updatePassword(token,userid,new PasswordBody(pass));
        loadCall.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(ChangePasswordActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
                if (response != null && !response.isError()) finish();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(ChangePasswordActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
