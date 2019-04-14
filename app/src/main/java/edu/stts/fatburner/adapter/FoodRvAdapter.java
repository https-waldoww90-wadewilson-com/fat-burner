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

public class FoodRvAdapter extends RecyclerView.Adapter<FoodRvAdapter.ViewHolder>{
    private Context context;
    private List<String> listFoods;
    private final rvListener listener;

    public FoodRvAdapter(Context context,List<String> foods,rvListener listener){
        this.listFoods = foods;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_food,parent,false);
        return new ViewHolder(itemView);
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
            productName = v.findViewById(R.id.rv_food_name);
        }

        public void bind(final String item, final rvListener listener) {
            productName.setText(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface rvListener {
        void onItemClick(String nama);
    }
}
