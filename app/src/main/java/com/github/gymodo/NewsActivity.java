package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.gymodo.adapters.NewsAdapter;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<String> descriptions = new ArrayList<>();
    private final ArrayList<String> links = new ArrayList<>();
    private final ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Hook
        recyclerView = findViewById(R.id.newsRecyclerView);

        getNewsData();

    }

    private void getNewsData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.XML_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //Convert string to xml
                            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                            Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));

                            for (int i = 1; i < parse.getElementsByTagName("item").getLength(); i++) {
                                //Get description field
                                String descriptionFormatHtml = parse.getElementsByTagName("description").item(i).getTextContent();

                                //Extract image url and description text from descriptionFormatHtml
                                descriptions.add(descriptionFormatHtml.substring(descriptionFormatHtml.lastIndexOf("<div>") + 5, descriptionFormatHtml.lastIndexOf(("</div>"))));
                                images.add(descriptionFormatHtml.substring(descriptionFormatHtml.indexOf("https:"), descriptionFormatHtml.indexOf("\" ")));

                                titles.add(parse.getElementsByTagName("title").item(i).getTextContent());
                                links.add(parse.getElementsByTagName("link").item(i).getTextContent());
                            }

                        } catch (ParserConfigurationException | IOException | SAXException e) {
                            e.printStackTrace();
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        newsAdapter = new NewsAdapter(titles, descriptions, links, images, getApplicationContext());
                        recyclerView.setAdapter(newsAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}