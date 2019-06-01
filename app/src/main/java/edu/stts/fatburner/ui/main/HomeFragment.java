package edu.stts.fatburner.ui.main;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.ArticleAdapter;
import edu.stts.fatburner.data.model.Article;
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

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rview = v.findViewById(R.id.rview_article);
        refresh = v.findViewById(R.id.srl_home);
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
                getArticlesData();
            }
        });
        getArticlesData();
    }

    private void getArticlesData(){
        refresh.setRefreshing(true);
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
