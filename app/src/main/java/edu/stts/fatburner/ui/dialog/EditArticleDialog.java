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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.FoodCategoryRvAdapter;
import edu.stts.fatburner.data.model.Article;
import edu.stts.fatburner.data.model.FoodCategory;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.UpdateArticleBody;
import edu.stts.fatburner.data.network.body.UpdateLogFoodBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import edu.stts.fatburner.ui.main.AddArticleActivity;
import retrofit2.Call;
import retrofit2.Response;

public class EditArticleDialog extends DialogFragment implements View.OnClickListener {
    public Callback callback;
    private API mApiInterface;
    private ImageButton btnClose;
    private Button btnSave;
    private EditText etJudul,etIsi;
    private SharedPreferences prefs;
    private SweetAlertDialog pDialog;
    private Article article;

    public static EditArticleDialog newInstance(Article data){
        EditArticleDialog instance = new EditArticleDialog();
        Bundle b = new Bundle();
        b.putSerializable("article_data",data);
        instance.setArguments(b);
        return instance;
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
        View v = inflater.inflate(R.layout.dialog_edit_article, container, false);
        etJudul = v.findViewById(R.id.et_editarticle_title);
        etIsi = v.findViewById(R.id.et_editarticle_isi);
        btnClose = v.findViewById(R.id.btn_editarticle_close);
        btnSave = v.findViewById(R.id.btn_editarticle_save);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = getActivity().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        btnClose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        //load data artikel
        article = (Article) getArguments().getSerializable("article_data");
        //initialization retrofit
        mApiInterface = ApiClient.getClient().create(API.class);
        etIsi.setText(article.getIsi().toString());
        etJudul.setText(article.getJudul().toString());
    }

    private boolean isAllowed(){
        if(etJudul.getText().toString().equals("")||etIsi.getText().toString().equals("")){
            Toast.makeText(requireContext(), "Title and content must be filled in!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void updateLogFood(String id,String judul,String isi){
        //untuk dialog
        pDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String token = prefs.getString("token","");
        Call<InsertResponse> saveCall = mApiInterface.updateArticle(token,Integer.parseInt(id+""),new UpdateArticleBody(judul,isi));
        saveCall.enqueue(new retrofit2.Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(requireContext(),response.getMessage(), Toast.LENGTH_LONG).show();
                if(!response.isError()){
                    callback.success(true);
                    dismiss();
                }
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
            }
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_editarticle_close){
            dismiss();
        }else if(id == R.id.btn_editarticle_save){
            if(isAllowed()) updateLogFood(article.getArtikelid(),etJudul.getText().toString(),etIsi.getText().toString());
        }
    }

    public interface Callback {
        void success(Boolean flag);
    }
}
