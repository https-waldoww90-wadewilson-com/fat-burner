package edu.stts.fatburner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.LogWorkout;

public class LogWorkoutRvAdapter extends RecyclerView.Adapter<LogWorkoutRvAdapter.ViewHolder> {
    private Context context;
    private List<LogWorkout> listWorkout;
    private final rvListener listener;

    public LogWorkoutRvAdapter(Context context, List<LogWorkout> work, rvListener listener){
        this.listWorkout = work;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogWorkoutRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_logfood,parent,false);
        return new LogWorkoutRvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listWorkout.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listWorkout.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView workoutName;
        public TextView waktu;
        public TextView totkalori;

        public ViewHolder(View v){
            super(v);
            workoutName = v.findViewById(R.id.rvrow_logfood_name);
            waktu = v.findViewById(R.id.rvrow_logfood_porsi);
            totkalori = v.findViewById(R.id.rvrow_logfood_totalcal);
        }

        public void bind(final LogWorkout item, final rvListener listener) {
            workoutName.setText(item.getNama());
            waktu.setText(item.getWaktu_workout()+" minutes");
            totkalori.setText(String.valueOf(item.getKalori() * item.getWaktu_workout()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface rvListener {
        void onItemClick(LogWorkout workout);
    }
}
