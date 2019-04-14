package edu.stts.fatburner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import edu.stts.fatburner.adapter.ArticleAdapter;
import edu.stts.fatburner.classObject.Article;


public class HomeFragment extends Fragment {
    private RecyclerView rview;
    private ArticleAdapter adapt;
    private ArrayList<Article> listArticle;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String isi = "LALALALALALALALLALLALA" +
                "LILILILILILILILILLI" +
                "LULULULULULULULLULU" +
                "LELELELELELELELELELE" +
                "LOLOLOLOLOLOLOLOLOLO";
        listArticle = new ArrayList<Article>();
        listArticle.add(new Article("Cellin", "12 April 2019", isi));
        listArticle.add(new Article("Ming", "13 April 2019", isi));
        listArticle.add(new Article("Rui", "14 April 2019", isi));
        listArticle.add(new Article("Natan", "15 April 2019", isi));

        adapt = new ArticleAdapter(listArticle);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rview = v.findViewById(R.id.rview_article);

        rview.setLayoutManager(new LinearLayoutManager(requireContext()));
        rview.setAdapter(adapt);

        return v;
    }
}
