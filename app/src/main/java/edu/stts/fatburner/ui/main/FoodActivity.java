package edu.stts.fatburner.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.FoodRvAdapter;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.ui.dialog.ChooseFoodDialog;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView rvFood;
    private FoodRvAdapter rvAdapter;
    private List<Food> listFoods;
    private API mApiInterface;
    private SearchView searchView;
    private SharedPreferences prefs;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        rvFood = findViewById(R.id.rv_food);
        listFoods = new ArrayList<>();
        prefs = getApplicationContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //untuk judul activity
        setTitle("Foods");

        //Ambil data type (breakfast,lunch,dinner)
        final String type= getIntent().getStringExtra("type");

        //Ambil data category yang dipilih oleh user
        final String categoryName = getIntent().getStringExtra("category");

        //retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
        getFoodsData(categoryName);

        //settings adapter recycler view
        rvAdapter = new FoodRvAdapter(this, listFoods, new FoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(Food food) {
                ChooseFoodDialog dialog = ChooseFoodDialog.newInstance(food,type);
                dialog.setCallback(new ChooseFoodDialog.Callback() {
                    @Override
                    public void foodChoosen(boolean success) {
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
        rvFood.setLayoutManager(lm);
        rvFood.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFood.setAdapter(rvAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFoodsData(String category){
        //untuk dialog
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String token = prefs.getString("token","");
        Call<List<Food>> foodCall = mApiInterface.getFoods(token,category);
        foodCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> res) {
                List<Food> response = res.body();
                if (response != null) {
                    listFoods.clear();
                    listFoods.addAll(response);
                }
                rvAdapter.notifyDataSetChanged();
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(FoodActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
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

    private List<Food> filter(String kata){
        List<Food> listFiltered = new ArrayList<>();
        for(int i=0;i<listFoods.size();i++){
            if(listFoods.get(i).getNama().toLowerCase().contains(kata.toLowerCase())) listFiltered.add(listFoods.get(i));
        }
        return listFiltered;
    }
}
