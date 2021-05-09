package com.github.gymodo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.exercise.Serie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Adapter for Series.
 */
public class SeriesAdapter extends ListAdapter<Serie, SeriesAdapter.ViewHolder> {

    private String rutineId;

    public SeriesAdapter() {
        super(DIFF_CALLBACK);
    }

    public SeriesAdapter(String rutineId) {
        super(DIFF_CALLBACK);
        this.rutineId = rutineId;
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
        holder.textWeight.setText(String.format("Weight: %d kg", serie.getWeight()));
        Exercise.getByID(serie.getExerciseId()).addOnSuccessListener(exercise -> holder.textName.setText(exercise.getName()));

        //Remove series
        holder.seriesConstraintLayout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Choose an options");
                MenuItem menuItem = menu.add(0, v.getId(), 1, "Delete");
                menuItem.setOnMenuItemClickListener(item -> {

                    if (rutineId != null) {
                        Routine.getByID(rutineId).addOnSuccessListener(routine -> {
                            Iterator<String> seriesIds = routine.getSeriesIds().iterator();
                            while (seriesIds.hasNext()) {
                                String serieId = seriesIds.next();
                                if (serie.getId().equalsIgnoreCase(serieId)) {
                                    seriesIds.remove();
                                }
                            }
                            routine.update().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    routine.getSeries().addOnSuccessListener(series -> {
                                        submitList(series);
                                    });
                                }
                            });

                        });
                    }
                    return true;
                });
            }
        });
    }

    public static final DiffUtil.ItemCallback<Serie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Serie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Serie oldItem, @NonNull Serie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Serie oldItem, @NonNull Serie newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.getReps() == newItem.getReps()
                    && oldItem.getWeight() == newItem.getWeight();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textReps;
        TextView textWeight;
        ConstraintLayout seriesConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.SeriesRowExerciseName);
            textReps = itemView.findViewById(R.id.SeriesRowReps);
            textWeight = itemView.findViewById(R.id.SeriesRowWeight);
            seriesConstraintLayout = itemView.findViewById(R.id.SeriesRowLayout);
        }
    }
}
