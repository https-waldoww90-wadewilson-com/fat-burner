package edu.stts.fatburner.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.FoodCategoryRvAdapter;
import edu.stts.fatburner.data.model.FoodCategory;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

public class AddArticleDialog extends DialogFragment implements View.OnClickListener {
    public Callback callback;
    private API mApiInterface;
    private ImageButton btnClose;
    private SharedPreferences prefs;
    private SweetAlertDialog pDialog;

    public static AddArticleDialog newInstance(){
        return new AddArticleDialog();
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
        View v = inflater.inflate(R.layout.dialog_add_article, container, false);
        btnClose = v.findViewById(R.id.btn_addarticle_close);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = getActivity().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        btnClose.setOnClickListener(this);
        //initialization retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_addarticle_close){
            dismiss();
        }
    }

    public interface Callback {
        void added(Boolean flag);
    }
}
