package edu.stts.fatburner.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.stts.fatburner.R;
import edu.stts.fatburner.classObject.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{

    private ArrayList<Article> articleList;
    //membuat constructor dari MenuAdapter yang menerima data ArrayList
    public ArticleAdapter(ArrayList<Article> articleList) {
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
        viewHolder.tvNama.setText(articleList.get(i).getNama());
        viewHolder.tvTanggal.setText(articleList.get(i).getTanggal());
        viewHolder.tvIsi.setText(articleList.get(i).getIsi()+"");
    }

    @Override
    public int getItemCount() {
        return (articleList!=null) ? articleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvTanggal, tvIsi;
        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            ivPost = itemView.findViewById(R.id.ivPost);
        }
    }
}
