package edu.stts.fatburner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import androidx.annotation.Nullable;
import edu.stts.fatburner.R;
import edu.stts.fatburner.adapter.listener.RvOptionListener;
import edu.stts.fatburner.data.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{
    private Context context;
    private List<Article> articleList;
    private static RvOptionListener listener;
    private String name;
    //membuat constructor dari MenuAdapter yang menerima data ArrayList
    public ArticleAdapter(Context context, List<Article> articleList,String name,RvOptionListener itemListener) {
        this.context = context;
        this.articleList = articleList;
        this.name = name;
        this.listener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.article_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.tvJudul.setText(articleList.get(i).getJudul());
        viewHolder.tvNama.setText(articleList.get(i).getNama());
        viewHolder.tvTanggal.setText(articleList.get(i).getDatecreated());
        viewHolder.tvIsi.setText(articleList.get(i).getIsi()+"");
        Glide.with(context)
                .load("http://www.pikukupikumu.com/fat/public/upload/"+articleList.get(i).getArtikelid()+".png")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(viewHolder.ivPost);
        viewHolder.tvOption.setVisibility(View.VISIBLE);
        if(!articleList.get(i).getNama().toLowerCase().equals(name.toLowerCase())) viewHolder.tvOption.setVisibility(View.GONE);
        else viewHolder.tvOption.setOnClickListener(v -> listener.optionClick(viewHolder.tvOption,i));
    }

    @Override
    public int getItemCount() {
        return (articleList!=null) ? articleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvNama, tvTanggal, tvIsi, tvOption;
        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvOption = itemView.findViewById(R.id.textViewOptions);
        }
    }
}
