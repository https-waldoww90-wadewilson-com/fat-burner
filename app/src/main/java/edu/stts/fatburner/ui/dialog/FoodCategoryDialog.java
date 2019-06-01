package edu.stts.fatburner.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.FoodCategoryRvAdapter;
import edu.stts.fatburner.data.model.FoodCategory;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodCategoryDialog extends DialogFragment implements View.OnClickListener {
    public Callback callback;
    private API mApiInterface;
    private RecyclerView rvCategory;
    private ImageButton btnClose;
    private FoodCategoryRvAdapter rvAdapter;
    private List<FoodCategory> listCategory;
    private SharedPreferences prefs;

    public static FoodCategoryDialog newInstance(){
        return new FoodCategoryDialog();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_food_category, container, false);
        rvCategory = v.findViewById(R.id.rv_foodcategory);
        btnClose = v.findViewById(R.id.btn_foodcategory_close);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listCategory = new ArrayList<>();
        prefs = getActivity().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        btnClose.setOnClickListener(this);
        //initialization retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
        //recyclerview
        rvAdapter = new FoodCategoryRvAdapter(requireContext(), listCategory, new FoodCategoryRvAdapter.rvListener() {
            @Override
            public void onItemClick(FoodCategory item) {
                callback.choosen(item.getKategori());
                dismiss();
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext());
        rvCategory.setLayoutManager(lm);
        rvCategory.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rvCategory.setAdapter(rvAdapter);
        loadFoodCategory();
    }

    private void loadFoodCategory(){
        String token = prefs.getString("token","");
        Call<List<FoodCategory>> foodCall = mApiInterface.getFoodCategory(token);
        foodCall.enqueue(new retrofit2.Callback<List<FoodCategory>>() {
            @Override
            public void onResponse(Call<List<FoodCategory>> call, Response<List<FoodCategory>> res) {
                List<FoodCategory> response = res.body();
                if (response != null) {
                    listCategory.clear();
                    listCategory.addAll(response);
                }
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FoodCategory>> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_foodcategory_close){
            dismiss();
        }
    }

    public interface Callback {
        void choosen(String categoryName);
    }
}
