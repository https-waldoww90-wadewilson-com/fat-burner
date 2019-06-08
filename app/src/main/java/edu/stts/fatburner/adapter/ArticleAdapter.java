package edu.stts.fatburner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.stts.fatburner.R;
import edu.stts.fatburner.data.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{
    private Context context;
    private List<Article> articleList;
    //membuat constructor dari MenuAdapter yang menerima data ArrayList
    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.article_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvJudul.setText(articleList.get(i).getJudul());
        viewHolder.tvNama.setText(articleList.get(i).getNama());
        viewHolder.tvTanggal.setText(articleList.get(i).getDatecreated());
        viewHolder.tvIsi.setText(articleList.get(i).getIsi()+"");
        Glide.with(context)
                .load(articleList.get(i).getImageurl())
                .into(viewHolder.ivPost);
    }

    @Override
    public int getItemCount() {
        return (articleList!=null) ? articleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvNama, tvTanggal, tvIsi;
        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            ivPost = itemView.findViewById(R.id.ivPost);
        }
    }
}
