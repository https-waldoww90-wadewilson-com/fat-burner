package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.LogFoodRvAdapter;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.ui.register.WorkoutActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiaryFragment extends Fragment implements View.OnClickListener{
    private LinearLayout linearBreakfast,linearLunch,linearDinner,linearSnacks,linearWorkout;
    private TextView tvEmptyBreakfast,tvTotalBreakfast,tvTotalCalories;
    private TextView tvEmptyLunch,tvTotalLunch,tvEmptyDinner,tvTotalDinner,tvEmptySnack,tvTotalSnack,tvEmptyWorkout,tvTotalWorkout;
    private API mApiInterface;
    private SharedPreferences pref;
    private List<LogFood> listBreakfast,listLunch,listDinner,listSnack;
    private RecyclerView rvBreakfast,rvLunch,rvDinner,rvSnack,rvWorkout;
    private LogFoodRvAdapter breakfastAdapter,lunchAdapter,dinnerAdapter,snackAdapter;
    private int totalBreakfast = 0,totalLunch = 0,totalDinner = 0,totalSnack=0,totalWorkout = 0;
    private SweetAlertDialog pDialog;

    public DiaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        linearBreakfast = v.findViewById(R.id.linear_breakfast);
        linearLunch = v.findViewById(R.id.linear_lunch);
        linearDinner = v.findViewById(R.id.linear_dinner);
        linearSnacks = v.findViewById(R.id.linear_snack);
        linearWorkout = v.findViewById(R.id.linear_workout);
        rvBreakfast = v.findViewById(R.id.rv_breakfast);
        rvLunch = v.findViewById(R.id.rv_lunch);
        rvDinner = v.findViewById(R.id.rv_dinner);
        rvSnack = v.findViewById(R.id.rv_snack);
        rvWorkout = v.findViewById(R.id.rv_workout);
        tvEmptyBreakfast = v.findViewById(R.id.tv_breakfast);
        tvTotalBreakfast = v.findViewById(R.id.totalBreakfast);
        tvEmptyLunch = v.findViewById(R.id.tv_lunch);
        tvTotalLunch = v.findViewById(R.id.totalLunch);
        tvEmptyDinner = v.findViewById(R.id.tv_dinner);
        tvTotalDinner = v.findViewById(R.id.totalDinner);
        tvEmptySnack = v.findViewById(R.id.tv_snack);
        tvTotalSnack = v.findViewById(R.id.totalSnack);
        tvEmptyWorkout = v.findViewById(R.id.tv_workout);
        tvTotalWorkout = v.findViewById(R.id.totalWorkout);
        tvTotalCalories = v.findViewById(R.id.tv_diary_totalcalories);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLogUser();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearBreakfast.setOnClickListener(this);
        linearLunch.setOnClickListener(this);
        linearDinner.setOnClickListener(this);
        linearSnacks.setOnClickListener(this);
        linearWorkout.setOnClickListener(this);
        mApiInterface = ApiClient.getClient().create(API.class);
        listBreakfast = new ArrayList<>();
        listLunch = new ArrayList<>();
        listDinner = new ArrayList<>();
        listSnack = new ArrayList<>();
        //rv breakfast
        breakfastAdapter = new LogFoodRvAdapter(requireContext(), listBreakfast, new LogFoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogFood food) {

            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext());
        rvBreakfast.setLayoutManager(lm);
        rvBreakfast.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvBreakfast.setAdapter(breakfastAdapter);

        //rv lunch
        lunchAdapter = new LogFoodRvAdapter(requireContext(), listLunch, new LogFoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogFood food) {

            }
        });
        RecyclerView.LayoutManager lmLunch = new LinearLayoutManager(requireContext());
        rvLunch.setLayoutManager(lmLunch);
        rvLunch.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvLunch.setAdapter(lunchAdapter);

        //rv dinner
        dinnerAdapter = new LogFoodRvAdapter(requireContext(), listDinner, new LogFoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogFood food) {

            }
        });
        RecyclerView.LayoutManager lmDinner = new LinearLayoutManager(requireContext());
        rvDinner.setLayoutManager(lmDinner);
        rvDinner.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvDinner.setAdapter(dinnerAdapter);

        //rv snack
        snackAdapter = new LogFoodRvAdapter(requireContext(), listSnack, new LogFoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogFood food) {

            }
        });
        RecyclerView.LayoutManager lmSnack = new LinearLayoutManager(requireContext());
        rvSnack.setLayoutManager(lmSnack);
        rvSnack.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvSnack.setAdapter(snackAdapter);

        //Untuk ambil session
        pref = requireContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        //untuk dialog
        pDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        loadLogUser();
    }

    private void loadLogUser(){
        int userid = pref.getInt("userID",-1);
        Call<List<LogFood>> loadCall = mApiInterface.getLogFood(userid);
        loadCall.enqueue(new Callback<List<LogFood>>() {
            @Override
            public void onResponse(Call<List<LogFood>> call, Response<List<LogFood>> res) {
                List<LogFood> response = res.body();
                displayData(response);
            }

            @Override
            public void onFailure(Call<List<LogFood>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayData(List<LogFood> data){
        totalBreakfast = 0; totalLunch = 0; totalDinner = 0; totalSnack=0;
        listBreakfast.clear();
        listLunch.clear();
        listDinner.clear();
        listSnack.clear();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getTipe().toLowerCase().equals("breakfast")) {
                totalBreakfast += data.get(i).getSatuan() * data.get(i).getKalori();
                listBreakfast.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("lunch")) {
                totalLunch += data.get(i).getSatuan() * data.get(i).getKalori();
                listLunch.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("dinner")) {
                totalDinner += data.get(i).getSatuan() * data.get(i).getKalori();
                listDinner.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("snack")) {
                totalSnack += data.get(i).getSatuan() * data.get(i).getKalori();
                listSnack.add(data.get(i));
            }
        }
        //untuk breakfast
        tvTotalBreakfast.setText(String.valueOf(totalBreakfast));
        if(listBreakfast.size() == 0){
            rvBreakfast.setVisibility(View.GONE);
            tvEmptyBreakfast.setVisibility(View.VISIBLE);
        }else{
            rvBreakfast.setVisibility(View.VISIBLE);
            tvEmptyBreakfast.setVisibility(View.GONE);
        }
        breakfastAdapter.notifyDataSetChanged();

        //untuk lunch
        tvTotalLunch.setText(String.valueOf(totalLunch) );
        if(listLunch.size() == 0){
            rvLunch.setVisibility(View.GONE);
            tvEmptyLunch.setVisibility(View.VISIBLE);
        }else{
            rvLunch.setVisibility(View.VISIBLE);
            tvEmptyLunch.setVisibility(View.GONE);
        }
        lunchAdapter.notifyDataSetChanged();

        //untuk dinner
        tvTotalDinner.setText(String.valueOf(totalDinner));
        if(listDinner.size() == 0){
            rvDinner.setVisibility(View.GONE);
            tvEmptyDinner.setVisibility(View.VISIBLE);
        }else{
            rvDinner.setVisibility(View.VISIBLE);
            tvEmptyDinner.setVisibility(View.GONE);
        }
        dinnerAdapter.notifyDataSetChanged();

        //untuk snack
        tvTotalSnack.setText(String.valueOf(totalSnack));
        if(listSnack.size() == 0){
            rvSnack.setVisibility(View.GONE);
            tvEmptySnack.setVisibility(View.VISIBLE);
        }else{
            rvSnack.setVisibility(View.VISIBLE);
            tvEmptySnack.setVisibility(View.GONE);
        }
        snackAdapter.notifyDataSetChanged();

        calculateTotalCalories();
    }

    private void calculateTotalCalories(){
        tvTotalCalories.setText("Total Calories : "+String.valueOf(totalBreakfast + totalLunch + totalDinner + totalSnack - totalWorkout) + " Cals");
        pDialog.dismissWithAnimation();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.linear_breakfast){
            Intent i = new Intent(getActivity(),FoodActivity.class);
            i.putExtra("type","breakfast");
            getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
        }else if(v.getId() == R.id.linear_lunch){
            Intent i = new Intent(getActivity(),FoodActivity.class);
            i.putExtra("type","lunch");
            getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
        }else if(v.getId() == R.id.linear_dinner){
            Intent i = new Intent(getActivity(),FoodActivity.class);
            i.putExtra("type","dinner");
            getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
        }else if(v.getId() == R.id.linear_snack){
            Intent i = new Intent(getActivity(),FoodActivity.class);
            i.putExtra("type","snack");
            getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
        }else if(v.getId() == R.id.linear_workout){
            startActivity(new Intent(getActivity(),WorkoutActivity.class));
        }
    }
}
