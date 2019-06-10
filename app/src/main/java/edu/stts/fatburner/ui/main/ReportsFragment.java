package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.ReportFoodRvAdapter;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.response.CalorieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment {
    private TextView tvTotalCalories,tvCalBreakfast,tvCalLunch,tvCalDinner,tvCalSnack,tvCalFood;
    private TextView tvPercentGoal,tvGoal,tvPercentBreakfast,tvPercentLunch,tvPercentDinner,tvPercentSnack;
    private Spinner timeSpinner;
    private RecyclerView rvFood;
    private LinearLayout llTotalFood,llGoal;
    private API mApiInterface;
    private SharedPreferences pref;
    private int totalBreakfast = 0,totalLunch = 0,totalDinner = 0,totalSnack = 0;
    private List<LogFood> listBreakfast,listLunch,listDinner,listSnack,listFood;
    private SweetAlertDialog pDialog;
    private ReportFoodRvAdapter rvAdapter;
    private PieChart pieChart;
    private String date="date";
    private double userCalorieGoal;
    private String[] xData = {"Breakfast","Lunch","Dinner","Snack"};

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
        tvPercentGoal = v.findViewById(R.id.tvPercentGoal);
        tvGoal = v.findViewById(R.id.tvGoal);
        pieChart = v.findViewById(R.id.pieChart);
        tvPercentBreakfast = v.findViewById(R.id.percentBreakfast);
        tvPercentLunch = v.findViewById(R.id.percentLunch);
        tvPercentDinner = v.findViewById(R.id.percentDinner);
        tvPercentSnack = v.findViewById(R.id.percentSnack);
        llGoal = v.findViewById(R.id.ll_goal);
        pieChartSettings();
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
                loadCalorieGoal();
                if(position == 0) {
                    date = "date";
                    loadLogFoodUser("date");
                }
                else if(position == 1) {
                    date = "month";
                    loadLogFoodUser("month");
                }
                else {
                    date = "year";
                    loadLogFoodUser("year");
                }
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

    private void pieChartSettings(){
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.getLegend().setEnabled(false);
    }

    private void loadDataPieChart(int breakfast, int lunch,int dinner,int snack){
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        yEntrys.add(new PieEntry(breakfast , 0));
        yEntrys.add(new PieEntry(lunch , 1));
        yEntrys.add(new PieEntry(dinner , 2));
        yEntrys.add(new PieEntry(snack , 3));

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setDrawValues(false);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFA500"));
        colors.add(Color.BLUE);
        colors.add(Color.parseColor("#FFC0CB"));
        colors.add(Color.parseColor("#009900"));
        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void loadCalorieGoal(){
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<CalorieResponse> loadCall = mApiInterface.getCalorie(token,userid);
        loadCall.enqueue(new Callback<CalorieResponse>() {
            @Override
            public void onResponse(Call<CalorieResponse> call, Response<CalorieResponse> res) {
                CalorieResponse response = res.body();
                userCalorieGoal = response.getCalorie();
                tvGoal.setText("Goal: "+(int)response.getCalorie()+" cal");
            }

            @Override
            public void onFailure(Call<CalorieResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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
        totalBreakfast = 0; totalLunch = 0; totalDinner = 0; totalSnack = 0;
        listBreakfast.clear();
        listLunch.clear();
        listDinner.clear();
        listSnack.clear();

        for(int i=0;i<data.size();i++){
            Log.d("COBA",i+"");
            Log.d("COBA",data.get(i).getTipe()+"");
            Log.d("COBA",data.get(i).getJumlah()+","+data.get(i).getKalori()+"");
            if(data.get(i).getTipe().toLowerCase().equals("breakfast")) {
                Log.d("COBA","msk breakfast");
                totalBreakfast += data.get(i).getJumlah() * data.get(i).getKalori();
                listBreakfast.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("lunch")) {
                Log.d("COBA","msk lunch");
                totalLunch += data.get(i).getJumlah() * data.get(i).getKalori();
                listLunch.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("dinner")) {
                Log.d("COBA","msk dinner");
                totalDinner += data.get(i).getJumlah() * data.get(i).getKalori();
                listDinner.add(data.get(i));
            }else if(data.get(i).getTipe().toLowerCase().equals("snack")) {
                Log.d("COBA","msk snack");
                totalSnack += data.get(i).getJumlah() * data.get(i).getKalori();
                listSnack.add(data.get(i));
            }
        }


        tvCalBreakfast.setText(String.valueOf(totalBreakfast));
        tvCalLunch.setText(String.valueOf(totalLunch));
        tvCalDinner.setText(String.valueOf(totalDinner));
        tvCalSnack.setText(String.valueOf(totalSnack));

        Log.d("COBA","setelah for");
        Log.d("COBA","breakfast"+totalBreakfast+"");
        Log.d("COBA","lunch"+totalLunch+"");
        Log.d("COBA","dinner"+totalDinner+"");
        Log.d("COBA","snack"+totalSnack+"");


        displayFood(data);
        if(totalBreakfast>0 || totalLunch>0 || totalDinner>0 || totalSnack>0){
            pieChart.setVisibility(View.VISIBLE);
            loadDataPieChart(totalBreakfast,totalLunch,totalDinner,totalSnack);
        }else pieChart.setVisibility(View.GONE);
        calculateTotalCalories();
        calculatePercent(totalBreakfast,totalLunch,totalDinner,totalSnack);
        if(date.equals("date")) {
            calculatePercentGoal(totalBreakfast,totalLunch,totalDinner,totalSnack);
            llGoal.setVisibility(View.VISIBLE);
        }
        else llGoal.setVisibility(View.GONE);
    }

    private void calculatePercentGoal(int breakfast, int lunch,int dinner,int snack){
        Log.d("COBA","calculatePercentGoal");
        Log.d("COBA","setelah for");
        Log.d("COBA","breakfast"+breakfast+"");
        Log.d("COBA","lunch"+lunch+"");
        Log.d("COBA","dinner"+dinner+"");
        Log.d("COBA","snack"+snack+"");
        int total = breakfast + lunch + dinner + snack;
        double percent = (double) total / userCalorieGoal * 100;
        Log.d("COBA",percent+"");
        tvPercentGoal.setText((int) percent + "% of goal");
        if(percent > 100) tvPercentGoal.setTextColor(Color.parseColor("#FF0000"));
        else tvPercentGoal.setTextColor(Color.parseColor("#7F7F7F"));
    }

    private void calculatePercent(int breakfast, int lunch,int dinner,int snack){
        int total = breakfast + lunch + dinner + snack;
        DecimalFormat df = new DecimalFormat("0.00");
        float breakfastPercent =(float) breakfast * 100 / total ;
        float lunchPercent = (float) lunch * 100 / total;
        float dinnerPercent = (float)dinner *100 / total;
        float snackPercent = (float)snack *100 / total;

        if(breakfast > 0) tvPercentBreakfast.setText(df.format(breakfastPercent)+" %");
        else tvPercentBreakfast.setText("0 %");
        if(lunch > 0)tvPercentLunch.setText(df.format(lunchPercent)+" %");
        else tvPercentLunch.setText("0 %");
        if(dinner > 0)tvPercentDinner.setText(df.format(dinnerPercent)+" %");
        else tvPercentDinner.setText("0 %");
        if(snack > 0)tvPercentSnack.setText(df.format(snackPercent)+" %");
        else tvPercentSnack.setText("0 %");
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
            if(ada) temp.get(index).setJumlah(temp.get(index).getJumlah()+data.get(i).getJumlah());
            else temp.add(data.get(i));
        }
        listFood.clear();
        listFood.addAll(temp);
        rvAdapter.notifyDataSetChanged();
        if(data.size() == 0) llTotalFood.setVisibility(View.GONE);
        else llTotalFood.setVisibility(View.VISIBLE);
    }
}
