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
import edu.stts.fatburner.adapter.ScheduleRvAdapter;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.response.InsertResponse;
import edu.stts.fatburner.data.network.response.ScheduleResponse;
import edu.stts.fatburner.ui.dialog.EditFoodDialog;
import edu.stts.fatburner.ui.dialog.FoodCategoryDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment implements View.OnClickListener{
    private LinearLayout linearBreakfast,linearLunch,linearDinner,linearSnacks;
    private TextView tvEmptyBreakfast,tvEmptyLunch,tvEmptyDinner,tvEmptySnack;
    private API mApiInterface;
    private List<ScheduleResponse> listBreakfast,listLunch,listDinner,listSnack;
    private SharedPreferences pref;
    private RecyclerView rvBreakfast,rvLunch,rvDinner,rvSnack;
    private ScheduleRvAdapter breakfastAdapter,lunchAdapter,dinnerAdapter,snackAdapter;
    private SweetAlertDialog deleteDialog;

    public ScheduleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        linearBreakfast = v.findViewById(R.id.linear_breakfast);
        linearLunch = v.findViewById(R.id.linear_lunch);
        linearDinner = v.findViewById(R.id.linear_dinner);
        linearSnacks = v.findViewById(R.id.linear_snack);
        tvEmptyBreakfast = v.findViewById(R.id.tv_breakfast);
        tvEmptyLunch = v.findViewById(R.id.tv_lunch);
        tvEmptyDinner = v.findViewById(R.id.tv_dinner);
        tvEmptySnack = v.findViewById(R.id.tv_snack);
        rvBreakfast = v.findViewById(R.id.rv_breakfast);
        rvLunch = v.findViewById(R.id.rv_lunch);
        rvDinner = v.findViewById(R.id.rv_dinner);
        rvSnack = v.findViewById(R.id.rv_snack);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearBreakfast.setOnClickListener(this);
        linearLunch.setOnClickListener(this);
        linearDinner.setOnClickListener(this);
        linearSnacks.setOnClickListener(this);
        mApiInterface = ApiClient.getClient().create(API.class);
        pref = requireContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        listBreakfast = new ArrayList<>();
        listLunch = new ArrayList<>();
        listDinner = new ArrayList<>();
        listSnack = new ArrayList<>();

        //rv breakfast
        breakfastAdapter = new ScheduleRvAdapter(requireContext(), listBreakfast, new ScheduleRvAdapter.rvListener() {
            @Override
            public void onItemClick(ScheduleResponse food) {
                deleteDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
                deleteDialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this data!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteLogFood(Integer.parseInt(food.getId_log()+""));
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext());
        rvBreakfast.setLayoutManager(lm);
        rvBreakfast.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvBreakfast.setAdapter(breakfastAdapter);

        //rv lunch
        lunchAdapter = new ScheduleRvAdapter(requireContext(), listLunch, new ScheduleRvAdapter.rvListener() {
            @Override
            public void onItemClick(ScheduleResponse food) {
                deleteDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
                deleteDialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this data!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteLogFood(Integer.parseInt(food.getId_log()+""));
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
        RecyclerView.LayoutManager lmLunch = new LinearLayoutManager(requireContext());
        rvLunch.setLayoutManager(lmLunch);
        rvLunch.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvLunch.setAdapter(lunchAdapter);

        //rv dinner
        dinnerAdapter = new ScheduleRvAdapter(requireContext(), listDinner, new ScheduleRvAdapter.rvListener() {
            @Override
            public void onItemClick(ScheduleResponse food) {
                deleteDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
                deleteDialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this data!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteLogFood(Integer.parseInt(food.getId_log()+""));
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
        RecyclerView.LayoutManager lmDinner = new LinearLayoutManager(requireContext());
        rvDinner.setLayoutManager(lmDinner);
        rvDinner.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvDinner.setAdapter(dinnerAdapter);

        //rv snack
        snackAdapter = new ScheduleRvAdapter(requireContext(), listSnack, new ScheduleRvAdapter.rvListener() {
            @Override
            public void onItemClick(ScheduleResponse food) {
                deleteDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
                deleteDialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this data!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                deleteLogFood(Integer.parseInt(food.getId_log()+""));
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
        RecyclerView.LayoutManager lmSnack = new LinearLayoutManager(requireContext());
        rvSnack.setLayoutManager(lmSnack);
        rvSnack.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvSnack.setAdapter(snackAdapter);

        loadSchedule();
    }

    private void loadSchedule(){
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<List<ScheduleResponse>> loadCall = mApiInterface.getSchedule(token,userid,"date");
        loadCall.enqueue(new Callback<List<ScheduleResponse>>() {
            @Override
            public void onResponse(Call<List<ScheduleResponse>> call, Response<List<ScheduleResponse>> res) {
                List<ScheduleResponse> response = res.body();
                displaySchedule(response);
            }

            @Override
            public void onFailure(Call<List<ScheduleResponse>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteLogFood(int id){
        //untuk dialog
        deleteDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        deleteDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        deleteDialog.setTitleText("Deleting...");
        deleteDialog.setCancelable(false);
        deleteDialog.show();

        String token = pref.getString("token","");
        Call<InsertResponse> deleteCall = mApiInterface.deleteSchedule(token,id);
        deleteCall.enqueue(new retrofit2.Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(requireContext(),response.getMessage(), Toast.LENGTH_LONG).show();
                if(!response.isError()){
                    loadSchedule();
                }
                deleteDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                deleteDialog.dismissWithAnimation();
            }
        });
    }

    private void displaySchedule(List<ScheduleResponse> data) {
        listBreakfast.clear();
        listLunch.clear();
        listDinner.clear();
        listSnack.clear();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getTipe().toLowerCase().equals("breakfast")) {
                listBreakfast.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("lunch")) {
                listLunch.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("dinner")) {
                listDinner.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("snack")) {
                listSnack.add(data.get(i));
            }
        }
        //untuk breakfast
        if(listBreakfast.size() == 0){
            rvBreakfast.setVisibility(View.GONE);
            tvEmptyBreakfast.setVisibility(View.VISIBLE);
        }else{
            rvBreakfast.setVisibility(View.VISIBLE);
            tvEmptyBreakfast.setVisibility(View.GONE);
        }
        breakfastAdapter.notifyDataSetChanged();

        //untuk lunch
        if(listLunch.size() == 0){
            rvLunch.setVisibility(View.GONE);
            tvEmptyLunch.setVisibility(View.VISIBLE);
        }else{
            rvLunch.setVisibility(View.VISIBLE);
            tvEmptyLunch.setVisibility(View.GONE);
        }
        lunchAdapter.notifyDataSetChanged();

        //untuk dinner
        if(listDinner.size() == 0){
            rvDinner.setVisibility(View.GONE);
            tvEmptyDinner.setVisibility(View.VISIBLE);
        }else{
            rvDinner.setVisibility(View.VISIBLE);
            tvEmptyDinner.setVisibility(View.GONE);
        }
        dinnerAdapter.notifyDataSetChanged();

        //untuk snack
        if(listSnack.size() == 0){
            rvSnack.setVisibility(View.GONE);
            tvEmptySnack.setVisibility(View.VISIBLE);
        }else{
            rvSnack.setVisibility(View.VISIBLE);
            tvEmptySnack.setVisibility(View.GONE);
        }
        snackAdapter.notifyDataSetChanged();
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
                    i.putExtra("from","schedule");
                    getActivity().startActivityForResult(i,MainActivity.CODE_SCHEDULE);
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
                    i.putExtra("from","schedule");
                    getActivity().startActivityForResult(i,MainActivity.CODE_SCHEDULE);
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
                    i.putExtra("from","schedule");
                    getActivity().startActivityForResult(i,MainActivity.CODE_SCHEDULE);
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
                    i.putExtra("from","schedule");
                    getActivity().startActivityForResult(i,MainActivity.CODE_SCHEDULE);
                }
            });
            dialog.show(getFragmentManager(), "tag");
        }
    }
}
