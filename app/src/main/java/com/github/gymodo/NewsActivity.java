package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.gymodo.news.News;
import com.github.gymodo.news.SimpleXmlRequest;

import java.util.ArrayList;


public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private static String XML_URL = "https://www.runtastic.com/blog/en/feed/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Hook
        recyclerView = findViewById(R.id.newsRecyclerView);

        /*
        NewsAdapter newsAdapter = new NewsAdapter(titles, descriptions, this);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        */
        getNewsData();
    }

    private void getNewsData() {
        SimpleXmlRequest<News> simpleRequest = new SimpleXmlRequest<News>(Request.Method.GET, XML_URL, News.class,
                new Response.Listener<News>()
                {
                    @Override
                    public void onResponse(News response) {
                        // response Object

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
    }
}