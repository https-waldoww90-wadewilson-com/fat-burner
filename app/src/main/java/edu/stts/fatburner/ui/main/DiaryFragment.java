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
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.LogFoodRvAdapter;
import edu.stts.fatburner.adapter.LogWorkoutRvAdapter;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.LogWorkout;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.ui.dialog.EditFoodDialog;
import edu.stts.fatburner.ui.dialog.EditWorkoutDialog;
import edu.stts.fatburner.ui.dialog.FoodCategoryDialog;
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
    private List<LogWorkout> listWorkout;
    private RecyclerView rvBreakfast,rvLunch,rvDinner,rvSnack,rvWorkout;
    private LogFoodRvAdapter breakfastAdapter,lunchAdapter,dinnerAdapter,snackAdapter;
    private LogWorkoutRvAdapter workoutAdapter;
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
        loadLogFoodUser();
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
        listWorkout = new ArrayList<>();

        //rv breakfast
        breakfastAdapter = new LogFoodRvAdapter(requireContext(), listBreakfast, new LogFoodRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogFood food) {
                EditFoodDialog dialog = EditFoodDialog.newInstance(food);
                dialog.setCallback(new EditFoodDialog.Callback() {
                    @Override
                    public void perform(boolean success) {
                        loadLogFoodUser();
                        loadLogWorkoutUser();
                    }
                });
                dialog.show(getFragmentManager(), "tag");
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
                EditFoodDialog dialog = EditFoodDialog.newInstance(food);
                dialog.setCallback(new EditFoodDialog.Callback() {
                    @Override
                    public void perform(boolean success) {
                        loadLogFoodUser();
                        loadLogWorkoutUser();
                    }
                });
                dialog.show(getFragmentManager(), "tag");
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
                EditFoodDialog dialog = EditFoodDialog.newInstance(food);
                dialog.setCallback(new EditFoodDialog.Callback() {
                    @Override
                    public void perform(boolean success) {
                        loadLogFoodUser();
                        loadLogWorkoutUser();
                    }
                });
                dialog.show(getFragmentManager(), "tag");
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
                EditFoodDialog dialog = EditFoodDialog.newInstance(food);
                dialog.setCallback(new EditFoodDialog.Callback() {
                    @Override
                    public void perform(boolean success) {
                        loadLogFoodUser();
                        loadLogWorkoutUser();
                    }
                });
                dialog.show(getFragmentManager(), "tag");
            }
        });
        RecyclerView.LayoutManager lmSnack = new LinearLayoutManager(requireContext());
        rvSnack.setLayoutManager(lmSnack);
        rvSnack.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvSnack.setAdapter(snackAdapter);

        //rv workout
        workoutAdapter = new LogWorkoutRvAdapter(requireContext(), listWorkout, new LogWorkoutRvAdapter.rvListener() {
            @Override
            public void onItemClick(LogWorkout workout) {
                EditWorkoutDialog dialog = EditWorkoutDialog.newInstance(workout);
                dialog.setCallback(new EditWorkoutDialog.Callback() {
                    @Override
                    public void perform(boolean success) {
                        loadLogFoodUser();
                        loadLogWorkoutUser();
                    }
                });
                dialog.show(getFragmentManager(), "tag");
            }
        });
        RecyclerView.LayoutManager lmWorkout = new LinearLayoutManager(requireContext());
        rvWorkout.setLayoutManager(lmWorkout);
        rvWorkout.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvWorkout.setAdapter(workoutAdapter);

        //Untuk ambil session
        pref = requireContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        //untuk dialog
        pDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        loadLogFoodUser();
        loadLogWorkoutUser();
    }

    private void loadLogFoodUser(){
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<List<LogFood>> loadCall = mApiInterface.getLogFood(token,userid,"date");
        loadCall.enqueue(new Callback<List<LogFood>>() {
            @Override
            public void onResponse(Call<List<LogFood>> call, Response<List<LogFood>> res) {
                List<LogFood> response = res.body();
                displayLogFoodData(response);
            }

            @Override
            public void onFailure(Call<List<LogFood>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadLogWorkoutUser(){
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<List<LogWorkout>> loadCall = mApiInterface.getLogWorkout(token,userid,"date");
        loadCall.enqueue(new Callback<List<LogWorkout>>() {
            @Override
            public void onResponse(Call<List<LogWorkout>> call, Response<List<LogWorkout>> res) {
                List<LogWorkout> response = res.body();
                displayLogWorkoutData(response);
            }

            @Override
            public void onFailure(Call<List<LogWorkout>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayLogWorkoutData(List<LogWorkout> data){
        totalWorkout = 0;
        listWorkout.clear();
        listWorkout.addAll(data);
        workoutAdapter.notifyDataSetChanged();
        for(int i=0;i<data.size();i++){
            totalWorkout += data.get(i).getWaktu_workout() * data.get(i).getKalori();
        }
        tvTotalWorkout.setText(String.valueOf(totalWorkout));
        if(listWorkout.size() == 0){
            rvWorkout.setVisibility(View.GONE);
            tvEmptyWorkout.setVisibility(View.VISIBLE);
        }else{
            rvWorkout.setVisibility(View.VISIBLE);
            tvEmptyWorkout.setVisibility(View.GONE);
        }
        calculateTotalCalories();
    }

    private void displayLogFoodData(List<LogFood> data){
        totalBreakfast = 0; totalLunch = 0; totalDinner = 0; totalSnack=0;
        listBreakfast.clear();
        listLunch.clear();
        listDinner.clear();
        listSnack.clear();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getTipe().toLowerCase().equals("breakfast")) {
                totalBreakfast += data.get(i).getJumlah() * data.get(i).getKalori();
                listBreakfast.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("lunch")) {
                totalLunch += data.get(i).getJumlah() * data.get(i).getKalori();
                listLunch.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("dinner")) {
                totalDinner += data.get(i).getJumlah() * data.get(i).getKalori();
                listDinner.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("snack")) {
                totalSnack += data.get(i).getJumlah() * data.get(i).getKalori();
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
            FoodCategoryDialog dialog = FoodCategoryDialog.newInstance();
            dialog.setCallback(new FoodCategoryDialog.Callback() {
                @Override
                public void choosen(String categoryName) {
                    Intent i = new Intent(getActivity(),FoodActivity.class);
                    i.putExtra("type","breakfast");
                    i.putExtra("category",categoryName);
                    getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
                }
            });
            dialog.show(getFragmentManager(), "tag");
        }
        else if(v.getId() == R.id.linear_lunch){
            FoodCategoryDialog dialog = FoodCategoryDialog.newInstance();
            dialog.setCallback(new FoodCategoryDialog.Callback() {
                @Override
                public void choosen(String categoryName) {
                    Intent i = new Intent(getActivity(),FoodActivity.class);
                    i.putExtra("type","lunch");
                    i.putExtra("category",categoryName);
                    getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
                }
            });
            dialog.show(getFragmentManager(), "tag");
        }
        else if(v.getId() == R.id.linear_dinner){
            FoodCategoryDialog dialog = FoodCategoryDialog.newInstance();
            dialog.setCallback(new FoodCategoryDialog.Callback() {
                @Override
                public void choosen(String categoryName) {
                    Intent i = new Intent(getActivity(),FoodActivity.class);
                    i.putExtra("type","dinner");
                    i.putExtra("category",categoryName);
                    getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
                }
            });
            dialog.show(getFragmentManager(), "tag");
        }
        else if(v.getId() == R.id.linear_snack){
            FoodCategoryDialog dialog = FoodCategoryDialog.newInstance();
            dialog.setCallback(new FoodCategoryDialog.Callback() {
                @Override
                public void choosen(String categoryName) {
                    Intent i = new Intent(getActivity(),FoodActivity.class);
                    i.putExtra("type","snack");
                    i.putExtra("category",categoryName);
                    getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
                }
            });
            dialog.show(getFragmentManager(), "tag");
        }else if(v.getId() == R.id.linear_workout){
            Intent i = new Intent(getActivity(),WorkoutActivity.class);
            getActivity().startActivityForResult(i,MainActivity.CODE_INFOFOOD);
        }
    }
}
