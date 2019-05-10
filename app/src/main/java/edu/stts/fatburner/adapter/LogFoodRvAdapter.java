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

public class LogFoodRvAdapter extends RecyclerView.Adapter<LogFoodRvAdapter.ViewHolder> {
    private Context context;
    private List<LogFood> listFoods;
    private final rvListener listener;

    public LogFoodRvAdapter(Context context,List<LogFood> foods,rvListener listener){
        this.listFoods = foods;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogFoodRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_logfood,parent,false);
        return new LogFoodRvAdapter.ViewHolder(itemView);
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
        public TextView porsi;
        public TextView totkalori;

        public ViewHolder(View v){
            super(v);
            productName = v.findViewById(R.id.rvrow_logfood_name);
            porsi = v.findViewById(R.id.rvrow_logfood_porsi);
            totkalori = v.findViewById(R.id.rvrow_logfood_totalcal);
        }

        public void bind(final LogFood item, final rvListener listener) {
            productName.setText(item.getNama());
            porsi.setText(item.getSatuan()+" X "+ " "+item.getBerat()+" gram");
            totkalori.setText(String.valueOf(item.getKalori() * item.getSatuan()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface rvListener {
        void onItemClick(LogFood food);
    }
}
