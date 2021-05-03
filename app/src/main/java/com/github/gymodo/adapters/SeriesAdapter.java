package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Serie;

import java.util.List;

/**
 * Adapter for Series.
 */
public class SeriesAdapter extends ListAdapter<Serie, SeriesAdapter.ViewHolder> {

    public SeriesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Serie serie = getItem(position);
        holder.textReps.setText(String.format("Reps: %d", serie.getReps()));
        holder.textWeight.setText(String.format("Reps: %d", serie.getWeight()));
        Exercise.getByID(serie.getExerciseId()).addOnSuccessListener(exercise -> holder.textName.setText(exercise.getName()));
    }

    public static final DiffUtil.ItemCallback<Serie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Serie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Serie oldItem, @NonNull Serie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Serie oldItem, @NonNull Serie newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textReps;
        TextView textWeight;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.SeriesRowExerciseName);
            textReps = itemView.findViewById(R.id.SeriesRowReps);
            textWeight = itemView.findViewById(R.id.SeriesRowWeight);
            image = itemView.findViewById(R.id.SeriesRowImage);
        }
    }
}
