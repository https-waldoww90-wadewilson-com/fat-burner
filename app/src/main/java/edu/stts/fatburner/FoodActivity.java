package edu.stts.fatburner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.stts.fatburner.adapter.FoodRvAdapter;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView rvFood;
    private FoodRvAdapter rvAdapter;
    private List<String> listFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        setTitle("List Foods");
        rvFood = findViewById(R.id.rv_food);
        listFoods = new ArrayList<>();
        listFoods.add("Pizza");
        listFoods.add("Indomie");
        listFoods.add("Bakso");
        rvAdapter = new FoodRvAdapter(this, listFoods, new FoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(String nama) {

            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvFood.setLayoutManager(lm);
        rvFood.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFood.setAdapter(rvAdapter);
    }
}
