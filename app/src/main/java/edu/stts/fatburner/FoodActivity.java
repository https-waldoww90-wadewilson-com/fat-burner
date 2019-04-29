package edu.stts.fatburner;

import android.app.Activity;
import android.content.Intent;
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

import edu.stts.fatburner.adapter.FoodRvAdapter;
import edu.stts.fatburner.classObject.Food;
import edu.stts.fatburner.dialog.ChooseFoodDialog;
import edu.stts.fatburner.network.API;
import edu.stts.fatburner.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView rvFood;
    private FoodRvAdapter rvAdapter;
    private List<Food> listFoods;
    private API mApiInterface;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        rvFood = findViewById(R.id.rv_food);
        listFoods = new ArrayList<>();
        //untuk back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //untuk judul activity
        setTitle("Foods");

        //retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
        getFoodsData();

        //settings adapter recycler view
        rvAdapter = new FoodRvAdapter(this, listFoods, new FoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(Food food) {
                ChooseFoodDialog dialog = ChooseFoodDialog.newInstance(food);
                dialog.setCallback(new ChooseFoodDialog.Callback() {
                    @Override
                    public void foodChoosen(Food data) {
                        Intent i = new Intent();
                        i.putExtra("food_data",data);
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

    private void getFoodsData(){
        Call<List<Food>> foodCall = mApiInterface.getFoods();
        foodCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> res) {
                List<Food> response = res.body();
                if (response != null) {
                    listFoods.clear();
                    listFoods.addAll(response);
                }
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(FoodActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
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
