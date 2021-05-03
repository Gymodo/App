package com.github.gymodo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Serie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for Series with selection.
 */
public class SeriesSelectAdapter extends ListAdapter<Serie, SeriesSelectAdapter.ViewHolder> {
    ArrayList<Boolean> selectedItems;

    public SeriesSelectAdapter() {
        super(DIFF_CALLBACK);
        selectedItems = new ArrayList<>();
    }

    public boolean isPositionSelected(int position) {
        return selectedItems.get(position);
    }

    @Override
    public void onCurrentListChanged(@NonNull List<Serie> previousList, @NonNull List<Serie> currentList) {
        super.onCurrentListChanged(previousList, currentList);
        selectedItems = new ArrayList<>(Collections.nCopies(currentList.size(), false));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_select_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Serie serie = getItem(position);
        holder.textReps.setText(String.format("Reps: %d", serie.getReps()));
        holder.textWeight.setText(String.format("Reps: %d", serie.getWeight()));
        Exercise.getByID(serie.getExerciseId()).addOnSuccessListener(exercise -> holder.textName.setText(exercise.getName()));

        holder.layout.setOnClickListener(v -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
        });

        holder.checkBox.setOnCheckedChangeListener((v, c) -> {
            if (selectedItems.get(position) != null) {
                selectedItems.set(position, c);
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
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        CheckBox checkBox;
        TextView textName;
        TextView textReps;
        TextView textWeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.SeriesSelectRowConstraint);
            textName = itemView.findViewById(R.id.SeriesSelectRowExerciseName);
            textReps = itemView.findViewById(R.id.SeriesSelectRowReps);
            textWeight = itemView.findViewById(R.id.SeriesSelectRowWeight);
            checkBox = itemView.findViewById(R.id.SeriesSelectRowCheckbox);
        }

        public boolean isSelected() {
            return checkBox.isChecked();
        }
    }
}
