package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private ArrayList<String> titles;
    private ArrayList<String> description;
    private Context context;

    public NewsAdapter(ArrayList<String> titles, ArrayList<String> description, Context context) {
        this.titles = titles;
        this.description = description;
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
        holder.newsTitle.setText(titles.get(position));
        holder.newsDescription.setText(description.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle;
        TextView newsDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitleRow);
            newsDescription = itemView.findViewById(R.id.newsDescriptionRow);
        }
    }
}