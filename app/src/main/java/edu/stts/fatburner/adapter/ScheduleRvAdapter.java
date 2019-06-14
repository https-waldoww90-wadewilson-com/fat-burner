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
import edu.stts.fatburner.data.network.response.ScheduleResponse;

public class ScheduleRvAdapter extends RecyclerView.Adapter<ScheduleRvAdapter.ViewHolder> {
    private Context context;
    private List<ScheduleResponse> listFoods;
    private final rvListener listener;

    public ScheduleRvAdapter(Context context, List<ScheduleResponse> foods, rvListener listener){
        this.listFoods = foods;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScheduleRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_food_category,parent,false);
        return new ScheduleRvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listFoods.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView productName;

        public ViewHolder(View v){
            super(v);
            productName = v.findViewById(R.id.tv_foodcategory_name);
        }

        public void bind(final ScheduleResponse item, final rvListener listener) {
            productName.setText(item.getNama());
            productName.setText(item.getNama().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface rvListener {
        void onItemClick(ScheduleResponse food);
    }
}
