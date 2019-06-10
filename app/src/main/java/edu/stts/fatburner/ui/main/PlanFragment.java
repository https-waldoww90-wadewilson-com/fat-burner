package edu.stts.fatburner.ui.main;


import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.PlanRvAdapter;
import edu.stts.fatburner.data.network.API;

public class PlanFragment extends Fragment {
    private PlanRvAdapter rvAvoidAdapter,rvRecommendAdapater;
    private List<String> listAvoid,listRecommend;
    private SharedPreferences pref;
    private RecyclerView rvAvoid,rvRecommend;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        rvAvoid = v.findViewById(R.id.rv_avoid);
        rvRecommend = v.findViewById(R.id.rv_recommend);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAvoid = new ArrayList<>();
        listRecommend = new ArrayList<>();
        pref = getActivity().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);

        //settings adapter recycler view avoid
        rvAvoidAdapter = new PlanRvAdapter(requireContext(), listAvoid);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext());
        rvAvoid.setLayoutManager(lm);
        rvAvoid.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvAvoid.setAdapter(rvAvoidAdapter);

        //settings adapter recycler view recommend
        rvRecommendAdapater = new PlanRvAdapter(requireContext(), listRecommend);
        RecyclerView.LayoutManager lm1 = new LinearLayoutManager(requireContext());
        rvRecommend.setLayoutManager(lm1);
        rvRecommend.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvRecommend.setAdapter(rvRecommendAdapater);

        loadData(Integer.parseInt(pref.getString("goal","0")));
    }

    private void loadData(int goal){
        List<String> tempAvoid = new ArrayList<>();
        List<String> tempRecommend = new ArrayList<>();

        if(goal == 1){ // maintain current weight
            tempAvoid.add("Permen");
            tempAvoid.add("Steak sapi");
            tempRecommend.add("Telur");
            tempRecommend.add("Ikan Salmon");
            tempRecommend.add("Kentang Rebus");
        }else if(goal == 2){ // weight loss
            tempAvoid.add("Minuman manis");
            tempAvoid.add("Pastries");
            tempAvoid.add("Alcohol");
            tempRecommend.add("Sayuran hijau");
            tempRecommend.add("Kentang Rebus");
            tempRecommend.add("Buah");
        }else if(goal == 3){ // weight gain
            tempAvoid.add("Minuman manis");
            tempAvoid.add("Pastries");
            tempAvoid.add("Alcohol");
            tempRecommend.add("Susu");
            tempRecommend.add("Buah alpukat");
            tempRecommend.add("Nasi putih");
        }else if(goal == 4){ // reduce sugar blood
            tempAvoid.add("Minuman manis");
            tempAvoid.add("Es krim");
            tempRecommend.add("Sayuran hijau");
            tempRecommend.add("Buah alpukat");
            tempRecommend.add("Ikan salmon");
            tempRecommend.add("Kentang rebus");
        }else{ // reduce cholesterol
            tempAvoid.add("Gorengan");
            tempAvoid.add("Kulit ayam,sapi");
            tempAvoid.add("Makanan cepat saji");
            tempAvoid.add("Telur ayam");
            tempAvoid.add("Jeroan");
            tempRecommend.add("Oatmeal");
            tempRecommend.add("Kacang kedelai");
            tempRecommend.add("Buah alpukat");
            tempRecommend.add("Sayuran hijau");
        }

        listAvoid.clear();
        listAvoid.addAll(tempAvoid);
        rvAvoidAdapter.notifyDataSetChanged();

        listRecommend.clear();
        listRecommend.addAll(tempRecommend);
        rvRecommendAdapater.notifyDataSetChanged();
    }
}
