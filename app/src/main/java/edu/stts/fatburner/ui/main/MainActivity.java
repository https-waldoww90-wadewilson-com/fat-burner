package edu.stts.fatburner.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.FirebaseBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import edu.stts.fatburner.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNav;
    private SharedPreferences pref;
    private API mApiInterface;
    public static final int CODE_INFOFOOD = 1;
    public static final int CODE_SCHEDULE = 3;
    public static final int CODE_HOME = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        //Untuk Navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pref = getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        View header = navigationView.getHeaderView(0);
        TextView email = (TextView) header.findViewById(R.id.header_email);
        TextView name = (TextView) header.findViewById(R.id.header_name);
        email.setText(pref.getString("email",""));
        name.setText(pref.getString("name",""));

        //Untuk bottom navigation
        bottomNav = findViewById(R.id.main_bnav);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Set Default biar menu home ditampilkan duluan
        loadFragment(new HomeFragment());

        mApiInterface = ApiClient.getClient().create(API.class);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("COBA","Main:error");
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("COBA","Main:"+token);
                        saveFirebaseKey(token);
                    }
                });
    }

    private void saveFirebaseKey(String key){
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<InsertResponse> loadCall = mApiInterface.updateFirebaseToken(token,userid,new FirebaseBody(key));
        loadCall.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Event saat button navigation diclick
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bnav_diary:
                    setTitle("Diary");
                    fragment = new DiaryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bnav_home:
                    setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bnav_report:
                    setTitle("Reports");
                    fragment = new ReportsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bnav_plan:
                    setTitle("Meal Plans");
                    fragment = new PlanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.bnav_schedule:
                    setTitle("Schedule");
                    fragment = new ScheduleFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    //Untuk ganti fragment pada main activity
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE_INFOFOOD){
            if(resultCode == Activity.RESULT_OK){
                Boolean isInsertSuccess = data.getExtras().getBoolean("data");
                if(isInsertSuccess) loadFragment(new DiaryFragment());
            }
        }else if(requestCode == CODE_HOME){
            if(resultCode == Activity.RESULT_OK){
                loadFragment(new HomeFragment());
            }
        }if(requestCode == CODE_SCHEDULE){
            if(resultCode == Activity.RESULT_OK){
                Boolean isInsertSuccess = data.getExtras().getBoolean("data");
                if(isInsertSuccess) loadFragment(new ScheduleFragment());
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_diary) {
            setTitle("Diary");
            loadFragment(new DiaryFragment());
        } else if (id == R.id.nav_home) {
            setTitle("Home");
            loadFragment(new HomeFragment());
        } else if (id == R.id.nav_report) {
            setTitle("Reports");
            loadFragment(new ReportsFragment());
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }else if(id == R.id.nav_logout){
            deleteUsername();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }else if (id == R.id.nav_plans) {
            setTitle("Meal Plans");
            loadFragment(new PlanFragment());
        }else if( id == R.id.nav_pass){
            startActivity(new Intent(MainActivity.this,ChangePasswordActivity.class));
        }else if (id == R.id.nav_schedule) {
            setTitle("Schedule");
            loadFragment(new ScheduleFragment());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deleteUsername(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userID",-1);
        editor.apply();
    }
}
