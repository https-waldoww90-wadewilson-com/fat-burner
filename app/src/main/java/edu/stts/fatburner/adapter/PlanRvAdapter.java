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
import edu.stts.fatburner.data.model.FoodCategory;

public class PlanRvAdapter extends RecyclerView.Adapter<PlanRvAdapter.ViewHolder> {
    private Context context;
    private List<String> listPlan;

    public PlanRvAdapter(Context context, List<String> cat){
        this.listPlan = cat;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_food_category,parent,false);
        return new PlanRvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listPlan.get(position));
    }

    @Override
    public int getItemCount() {
        return listPlan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;

        public ViewHolder(View v){
            super(v);
            categoryName = v.findViewById(R.id.tv_foodcategory_name);
        }

        public void bind(final String item) {
            categoryName.setText(item);
        }
    }
}
