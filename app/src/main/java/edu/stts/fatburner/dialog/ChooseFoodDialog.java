package edu.stts.fatburner.dialog;

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
import edu.stts.fatburner.classObject.Food;

public class ChooseFoodDialog extends DialogFragment implements View.OnClickListener {
    private TextView tvFoodName,tvSatuan,tvKalori;
    private EditText etJumlah;
    private Button btnSave;
    private ImageButton btnClose;
    private Food data;
    public Callback callback;

    public static ChooseFoodDialog newInstance(Food data){
        ChooseFoodDialog instance = new ChooseFoodDialog();
        Bundle b = new Bundle();
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
                    tvKalori.setText(String.valueOf(jumlah * Integer.parseInt(data.getKalori())));
                }else tvKalori.setText("0");
            }
        });
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
        data = (Food) getArguments().getSerializable("food_data");
        tvFoodName.setText(data.getNama());
        tvSatuan.setText(data.getSatuan());
        tvKalori.setText(data.getKalori());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.dialog_choose_close){
            dismiss();
        }else if(id == R.id.dialog_choose_save){
            if(!etJumlah.getText().toString().equals("") && !etJumlah.getText().toString().equals("0")){
                data.setJumlah(Integer.parseInt(etJumlah.getText().toString()));
                callback.foodChoosen(data);
                dismiss();
            }else Toast.makeText(requireContext(),"Field size must not be empty!",Toast.LENGTH_LONG).show();
        }
    }

    public interface Callback {
        void foodChoosen(Food data);
    }
}


