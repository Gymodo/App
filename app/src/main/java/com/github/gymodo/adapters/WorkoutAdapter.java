package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Routine;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    List<Routine> workouts;
    Context context;

    public WorkoutAdapter(List<Routine> workouts, Context context) {
        this.workouts = workouts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workouts_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine workout = workouts.get(position);
        holder.name.setText(workout.getName());
        holder.desc.setText(workout.getDescription());
        holder.more.setOnClickListener(view -> {
            // TODO do something
            throw new UnsupportedOperationException("falta implementar");
        });
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView desc;
        ImageView more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.WorkoutRowTitle);
            desc = itemView.findViewById(R.id.WorkoutRowDescription);
            more = itemView.findViewById(R.id.WorkoutRowMore);
        }
    }
}
