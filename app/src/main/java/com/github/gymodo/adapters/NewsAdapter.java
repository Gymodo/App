package com.github.gymodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter for news.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<String> descriptions;
    private ArrayList<String> links;
    private ArrayList<String> images;
    private Context context;

    public NewsAdapter(ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<String> links, ArrayList<String> images, Context context) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.links = links;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_news_row, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        Picasso.get().load(images.get(position)).into(holder.newsImage);
        holder.newsTitle.setText(titles.get(position));
        holder.newsDescription.setText(descriptions.get(position));
        holder.newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDescription;
        ImageView newsImage;
        Button newsButton;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitleRow);
            newsDescription = itemView.findViewById(R.id.newsDescriptionRow);
            newsImage = itemView.findViewById(R.id.newsImageRow);
            newsButton = itemView.findViewById(R.id.newsButtonRow);
        }
    }
}