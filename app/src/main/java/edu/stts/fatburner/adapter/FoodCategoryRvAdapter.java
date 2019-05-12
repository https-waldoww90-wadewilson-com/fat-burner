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
import edu.stts.fatburner.data.model.LogFood;

public class FoodCategoryRvAdapter extends RecyclerView.Adapter<FoodCategoryRvAdapter.ViewHolder> {
    private Context context;
    private List<FoodCategory> listCategory;
    private final rvListener listener;

    public FoodCategoryRvAdapter(Context context, List<FoodCategory> cat, rvListener listener){
        this.listCategory = cat;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodCategoryRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_food_category,parent,false);
        return new FoodCategoryRvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listCategory.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;

        public ViewHolder(View v){
            super(v);
            categoryName = v.findViewById(R.id.tv_foodcategory_name);
        }

        public void bind(final FoodCategory item, final rvListener listener) {
            categoryName.setText(item.getKategori());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface rvListener {
        void onItemClick(FoodCategory category);
    }
}
