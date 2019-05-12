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
import edu.stts.fatburner.data.model.Workout;

public class WorkoutRvAdapter extends RecyclerView.Adapter<WorkoutRvAdapter.ViewHolder> {
    private Context context;
    private List<Workout> listWorkout;
    private final rvListener listener;

    public WorkoutRvAdapter(Context context, List<Workout> work, rvListener listener){
        this.listWorkout = work;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_workout,parent,false);
        return new WorkoutRvAdapter.ViewHolder(itemView);
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
        public TextView time;
        public TextView kalori;

        public ViewHolder(View v){
            super(v);
            workoutName = v.findViewById(R.id.rvrow_workout_name);
            time = v.findViewById(R.id.rvrow_workout_porsi);
            kalori = v.findViewById(R.id.rvrow_workout_kalori);
        }

        public void bind(final Workout item, final rvListener listener) {
            workoutName.setText(item.getNama()+"");
            time.setText(item.getWaktu()+" minute");
            kalori.setText(item.getKalori()+" cal");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void setFilter(List<Workout> listFiltered){
        listWorkout = listFiltered;
        notifyDataSetChanged();
    }

    public interface rvListener {
        void onItemClick(Workout category);
    }
}
