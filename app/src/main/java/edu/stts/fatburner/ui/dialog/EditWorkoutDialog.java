package edu.stts.fatburner.ui.dialog;

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
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.LogWorkout;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.UpdateLogFoodBody;
import edu.stts.fatburner.data.network.body.UpdateLogWorkoutBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import retrofit2.Call;
import retrofit2.Response;

public class EditWorkoutDialog extends DialogFragment implements View.OnClickListener {
    private LogWorkout logWorkout;
    public Callback callback;
    private TextView tvName,tvSatuan,tvKalori;
    private EditText etJumlah;
    private Button btnUpdate,btnDelete;
    private ImageButton btnClose;
    private API mApiInterface;

    public static EditWorkoutDialog newInstance(LogWorkout data){
        EditWorkoutDialog instance = new EditWorkoutDialog();
        Bundle b = new Bundle();
        b.putSerializable("workout_data",data);
        instance.setArguments(b);
        return instance;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_edit_workout, container, false);
        tvName = v.findViewById(R.id.dialog_editworkout_name);
        tvKalori = v.findViewById(R.id.dialog_editworkout_kalori);
        tvSatuan = v.findViewById(R.id.dialog_editworkout_satuan);
        etJumlah = v.findViewById(R.id.dialog_editworkout_jumlah);
        btnUpdate = v.findViewById(R.id.dialog_editworkout_update);
        btnClose = v.findViewById(R.id.dialog_editworkout_close);
        btnDelete = v.findViewById(R.id.dialog_editworkout_delete);
        btnUpdate.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
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
                    tvKalori.setText(String.valueOf(jumlah * Integer.parseInt(logWorkout.getKalori()+"")));
                }else tvKalori.setText("0");
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logWorkout = (LogWorkout) getArguments().getSerializable("workout_data");
        etJumlah.setText(logWorkout.getWaktu_workout()+"");
        etJumlah.setSelection(etJumlah.getText().length());
        tvName.setText(logWorkout.getNama()+"");
        tvSatuan.setText("minutes");
        tvKalori.setText(logWorkout.getKalori() * logWorkout.getWaktu_workout() +"");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.dialog_editworkout_close){
            dismiss();
        }else if(id == R.id.dialog_editworkout_update){
            if(!etJumlah.getText().toString().equals("") && !etJumlah.getText().toString().equals("0")){
                updateLogWorkout(Integer.parseInt(etJumlah.getText().toString()));
            }else Toast.makeText(requireContext(),"Field size must not be empty!",Toast.LENGTH_LONG).show();
        }else if(id == R.id.dialog_editworkout_delete){
            deleteLogWorkout();
        }
    }

    private void updateLogWorkout(int waktu){
        UpdateLogWorkoutBody body = new UpdateLogWorkoutBody(waktu);
        Call<InsertResponse> saveCall = mApiInterface.updateLogWorkout(logWorkout.getId_log(),body);
        saveCall.enqueue(new retrofit2.Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(requireContext(),response.getMessage(), Toast.LENGTH_LONG).show();
                if(!response.isError()){
                    callback.perform(true);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteLogWorkout(){
        Call<InsertResponse> deleteCall = mApiInterface.deleteLogWorkout(logWorkout.getId_log());
        deleteCall.enqueue(new retrofit2.Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(requireContext(),response.getMessage(), Toast.LENGTH_LONG).show();
                if(!response.isError()){
                    callback.perform(true);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(requireContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface Callback {
        void perform(boolean success);
    }
}
