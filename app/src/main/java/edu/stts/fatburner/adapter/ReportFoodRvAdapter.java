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

public class ReportFoodRvAdapter extends RecyclerView.Adapter<ReportFoodRvAdapter.ViewHolder> {
    private Context context;
    private List<LogFood> listFoods;

    public ReportFoodRvAdapter(Context context, List<LogFood> foods){
        this.listFoods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportFoodRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_report_food,parent,false);
        return new ReportFoodRvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listFoods.get(position));
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
            productName = v.findViewById(R.id.tv_reportfood_name);
            porsi = v.findViewById(R.id.tv_reportfood_jumlah);
            totkalori = v.findViewById(R.id.tv_reportfood_cal);
        }

        public void bind(final LogFood item) {
            productName.setText(item.getNama());
            porsi.setText("x"+item.getJumlah()+" =");
            totkalori.setText(String.valueOf(item.getKalori() * item.getJumlah()));
        }
    }
}
