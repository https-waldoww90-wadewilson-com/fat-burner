package edu.stts.fatburner.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.LogFoodBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseFoodDialog extends DialogFragment implements View.OnClickListener {
    private TextView tvFoodName,tvSatuan,tvKalori;
    private EditText etJumlah;
    private Button btnSave;
    private ImageButton btnClose;
    private Food chosenFood;
    private String type;
    private SharedPreferences sp;
    private API mApiInterface;
    public Callback callback;

    public static ChooseFoodDialog newInstance(Food data,String type){
        ChooseFoodDialog instance = new ChooseFoodDialog();
        Bundle b = new Bundle();
        b.putString("type",type);
        b.putSerializable("food_data",data);
        instance.setArguments(b);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_choose_food, container, false);
        tvFoodName = v.findViewById(R.id.dialog_choose_foodname);
        tvKalori = v.findViewById(R.id.dialog_choose_kalori);
        tvSatuan = v.findViewById(R.id.dialog_choose_satuan);
        etJumlah = v.findViewById(R.id.dialog_choose_jumlah);
        btnSave = v.findViewById(R.id.dialog_choose_save);
        btnClose = v.findViewById(R.id.dialog_choose_close);
        btnSave.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        mApiInterface = ApiClient.getClient().create(API.class);
        //listener buat edit text jika ada pergantian input
        etJumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    Integer jumlah = Integer.parseInt(s.toString());
                    tvKalori.setText(String.valueOf(jumlah * Integer.parseInt(chosenFood.getKalori())));
                }else tvKalori.setText("0");
            }
        });
        etJumlah.setSelection(etJumlah.getText().length());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = getContext().getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        //ambil data yg dipassing dari food activity
        type =  getArguments().getString("type");
        chosenFood = (Food) getArguments().getSerializable("food_data");
        tvFoodName.setText(chosenFood.getNama());
        tvSatuan.setText(chosenFood.getSatuan());
        tvKalori.setText(chosenFood.getKalori());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.dialog_choose_close){
            dismiss();
        }else if(id == R.id.dialog_choose_save){
            if(!etJumlah.getText().toString().equals("") && !etJumlah.getText().toString().equals("0")){
                saveLogFood(Integer.parseInt(etJumlah.getText().toString()));
            }else Toast.makeText(requireContext(),"Field size must not be empty!",Toast.LENGTH_LONG).show();
        }
    }

    private void saveLogFood(int jumlah){
        //ambil id user
        int userid = sp.getInt("userID",-1);
        String token = sp.getString("token","");
        LogFoodBody body = new LogFoodBody(userid,Integer.parseInt(chosenFood.getId()),type,jumlah);
        Call<InsertResponse> saveCall = mApiInterface.saveLogFood(token,body);
        saveCall.enqueue(new retrofit2.Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                if(!response.isError()){
                    callback.foodChoosen(true);
                    dismiss();
                }
                else Toast.makeText(requireContext(),response.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface Callback {
        void foodChoosen(boolean success);
    }
}


