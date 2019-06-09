package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.ArticleAdapter;
import edu.stts.fatburner.data.model.Article;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.LogWorkout;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView rview;
    private ArticleAdapter adapt;
    private List<Article> listArticle;
    private API mApiInterface;
    private SwipeRefreshLayout refresh;
    private SharedPreferences pref;
    private TextView tvFood,tvWorkout,tvWeight;
    private LinearLayout llAddArticle;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rview = v.findViewById(R.id.rview_article);
        refresh = v.findViewById(R.id.srl_home);
        tvFood = v.findViewById(R.id.textView3);
        tvWorkout = v.findViewById(R.id.textView4);
        tvWeight = v.findViewById(R.id.textView2);
        llAddArticle = v.findViewById(R.id.ll_create_article);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = getActivity().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        mApiInterface = ApiClient.getClient().create(API.class);
        listArticle = new ArrayList<>();
        //Recyclerview untuk artikel
        adapt = new ArticleAdapter(requireContext(),listArticle);
        rview.setLayoutManager(new LinearLayoutManager(requireContext()));
        rview.setAdapter(adapt);
        //Buat load ulang artikel
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //load food calories
                loadLogFoodUser("date");
                //load workout calories
                loadLogWorkoutUser();
                //load artikel
                getArticlesData();
            }
        });
        //load weight
        float weight = pref.getFloat("weight",0);
        tvWeight.setText(weight+" kg");
        //load food calories
        loadLogFoodUser("date");
        //load workout calories
        loadLogWorkoutUser();
        //load artikel
        getArticlesData();

        //Tambah artikel
        llAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),AddArticleActivity.class));
            }
        });
    }

    private void loadLogFoodUser(String flag){
        refresh.setRefreshing(true);
        int userid = pref.getInt("userID",-1);
        String token = pref.getString("token","");
        Call<List<LogFood>> loadCall = mApiInterface.getLogFood(token,userid,flag);
        loadCall.enqueue(new Callback<List<LogFood>>() {
            @Override
            public void onResponse(Call<List<LogFood>> call, Response<List<LogFood>> res) {
                List<LogFood> response = res.body();
                calculateFoodCalories(response);
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
                calculateWorkoutCalories(response);
            }

            @Override
            public void onFailure(Call<List<LogWorkout>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void calculateFoodCalories(List<LogFood> data){
        int cal = 0;
        for(int i=0;i<data.size();i++){
            cal +=  data.get(i).getJumlah() * data.get(i).getKalori();
        }
        tvFood.setText(cal + " cal");
    }

    private void calculateWorkoutCalories(List<LogWorkout> data){
        int cal = 0;
        for(int i=0;i<data.size();i++){
            cal +=  data.get(i).getWaktu_workout() * data.get(i).getKalori();
        }
        tvWorkout.setText(cal + " cal");
    }

    private void getArticlesData(){
        String token = pref.getString("token","");
        Call<List<Article>> articleCall = mApiInterface.getArticles(token);
        articleCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> res) {
                List<Article> response = res.body();
                if (response != null) {
                    listArticle.clear();
                    listArticle.addAll(response);
                    adapt.notifyDataSetChanged();
                }
                refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                refresh.setRefreshing(false);
            }
        });
    }
}
