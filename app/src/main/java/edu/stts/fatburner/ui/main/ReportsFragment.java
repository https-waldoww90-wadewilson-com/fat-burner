package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.ReportFoodRvAdapter;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsFragment extends Fragment {
    private TextView tvTotalCalories,tvCalBreakfast,tvCalLunch,tvCalDinner,tvCalSnack,tvCalFood;
    private Spinner timeSpinner;
    private RecyclerView rvFood;
    private LinearLayout llTotalFood;
    private API mApiInterface;
    private SharedPreferences pref;
    private int totalBreakfast = 0,totalLunch = 0,totalDinner = 0,totalSnack=0;
    private List<LogFood> listBreakfast,listLunch,listDinner,listSnack,listFood;
    private SweetAlertDialog pDialog;
    private ReportFoodRvAdapter rvAdapter;

    public ReportsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);
        tvTotalCalories  = v.findViewById(R.id.tv_report_totalCal);
        tvCalBreakfast = v.findViewById(R.id.tv_report_totalBreakfast);
        tvCalLunch = v.findViewById(R.id.tv_report_totalLunch);
        tvCalDinner = v.findViewById(R.id.tv_report_totalDinner);
        tvCalSnack = v.findViewById(R.id.tv_report_totalSnack);
        timeSpinner = v.findViewById(R.id.spinnerTime);
        llTotalFood =  v.findViewById(R.id.ll_report_food);
        rvFood = v.findViewById(R.id.rv_report_food);
        tvCalFood = v.findViewById(R.id.tv_report_totalfood);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiInterface = ApiClient.getClient().create(API.class);
        pref = requireContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        listBreakfast = new ArrayList<>();
        listLunch = new ArrayList<>();
        listDinner = new ArrayList<>();
        listSnack = new ArrayList<>();
        listFood = new ArrayList<>();

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) loadLogFoodUser("date");
                else if(position == 1) loadLogFoodUser("month");
                else loadLogFoodUser("year");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        rvAdapter = new ReportFoodRvAdapter(requireContext(), listFood);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext());
        rvFood.setLayoutManager(lm);
        rvFood.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvFood.setAdapter(rvAdapter);
    }

    private void loadLogFoodUser(String flag){
        //untuk dialog
        pDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<List<LogFood>> loadCall = mApiInterface.getLogFood(token,userid,flag);
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
                Log.d("COBA",data.get(i).getId_log()+","+data.get(i).getJumlah()+","+data.get(i).getKalori());
                totalDinner += data.get(i).getJumlah() * data.get(i).getKalori();
                listDinner.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("snack")) {
                totalSnack += data.get(i).getJumlah() * data.get(i).getKalori();
                listSnack.add(data.get(i));
            }
        }

        tvCalBreakfast.setText(String.valueOf(totalBreakfast));
        tvCalLunch.setText(String.valueOf(totalLunch));
        tvCalDinner.setText(String.valueOf(totalDinner));
        tvCalSnack.setText(String.valueOf(totalSnack));

        displayFood(data);
        calculateTotalCalories();
    }

    private void calculateTotalCalories(){
        tvTotalCalories.setText(String.valueOf(totalBreakfast + totalLunch + totalDinner + totalSnack));
        tvCalFood.setText(String.valueOf(totalBreakfast + totalLunch + totalDinner + totalSnack));
        pDialog.dismissWithAnimation();
    }

    private void displayFood(List<LogFood> data){
        List<LogFood> temp = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            boolean ada = false; int index = -1;
            for(int j=0;j<temp.size();j++){
                if(temp.get(j).getNama().toLowerCase().equals(data.get(i).getNama().toLowerCase())){
                    index = j;
                    ada = true;
                    break;
                }
            }
            if(ada) {
                Log.d("COBA",data.get(i).getId_log()+"");
                temp.get(index).setJumlah(temp.get(index).getJumlah()+data.get(i).getJumlah());
            }
            else temp.add(data.get(i));
        }
        listFood.clear();
        listFood.addAll(temp);
        rvAdapter.notifyDataSetChanged();
        if(data.size() == 0) llTotalFood.setVisibility(View.GONE);
        else llTotalFood.setVisibility(View.VISIBLE);
    }
}
