package edu.stts.fatburner.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.FoodRvAdapter;
import edu.stts.fatburner.adapter.WorkoutRvAdapter;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.data.model.Workout;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.ui.dialog.ChooseFoodDialog;
import edu.stts.fatburner.ui.dialog.ChooseWorkoutDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView rvWorkout;
    private WorkoutRvAdapter rvAdapter;
    private List<Workout> listWorkout;
    private API mApiInterface;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        rvWorkout = findViewById(R.id.rv_workoutactivity);
        listWorkout = new ArrayList<>();
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //untuk judul activity
        setTitle("Workouts");

        //retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
        getWorkoutData();

        //settings adapter recycler view
        rvAdapter = new WorkoutRvAdapter(this, listWorkout, new WorkoutRvAdapter.rvListener() {
            @Override
            public void onItemClick(Workout item) {
                ChooseWorkoutDialog dialog = ChooseWorkoutDialog.newInstance(item);
                dialog.setCallback(new ChooseWorkoutDialog.Callback() {
                    @Override
                    public void workoutChoosen(boolean success) {
                        Intent i = new Intent();
                        i.putExtra("data",success);
                        setResult(Activity.RESULT_OK,i);
                        finish();
                    }
                });
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvWorkout.setLayoutManager(lm);
        rvWorkout.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvWorkout.setAdapter(rvAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getWorkoutData(){
        Call<List<Workout>> workoutCall = mApiInterface.getWorkouts();
        workoutCall.enqueue(new Callback<List<Workout>>() {
            @Override
            public void onResponse(Call<List<Workout>> call, Response<List<Workout>> res) {
                List<Workout> response = res.body();
                Log.d("COBA",response.get(0).getNama()+","+response.get(0).getId()+","+response.get(0).getKalori()+","+response.get(0).getWaktu());
                if (response != null) {
                    listWorkout.clear();
                    listWorkout.addAll(response);
                }
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Workout>> call, Throwable t) {
                Toast.makeText(WorkoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.btn_food_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rvAdapter.setFilter(filter(s));
        return false;
    }

    private List<Workout> filter(String kata){
        List<Workout> listFiltered = new ArrayList<>();
        for(int i=0;i<listWorkout.size();i++){
            if(listWorkout.get(i).getNama().toLowerCase().contains(kata.toLowerCase())) listFiltered.add(listWorkout.get(i));
        }
        return listFiltered;
    }
}
