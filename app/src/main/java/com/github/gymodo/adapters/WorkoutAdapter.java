package com.github.gymodo.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.fragments.WorkoutDetailFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends ListAdapter<Routine, WorkoutAdapter.ViewHolder> {
    boolean detailOnSelect;

    public WorkoutAdapter(boolean detailOnSelect) {
        super(DIFF_CALLBACK);
        this.detailOnSelect = detailOnSelect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workouts_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine workout = getItem(position);
        holder.name.setText(workout.getName());
        holder.desc.setText(workout.getDescription());
        holder.layout.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString(WorkoutDetailFragment.ARG_ROUTINE_ID, workout.getId());
            navController.navigate(R.id.workout_list_to_detail, bundle);
        });

        //Remove workout
        holder.layout.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.setHeaderTitle("Choose an options");
            MenuItem menuItem = menu.add(0, v.getId(), 2, "Delete");

            menuItem.setOnMenuItemClickListener(item -> {
                workout.setAuthorId(null);
                workout.update().addOnSuccessListener(aVoid -> {
                    ArrayList<Routine> routines = new ArrayList<>(getCurrentList());
                    routines.remove(workout);
                    submitList(routines);
                });
                return true;
            });
        });

        /*
        holder.more.setOnClickListener(view -> {
            throw new UnsupportedOperationException("falta implementar");
        });
        */
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView name;
        TextView desc;
        ImageView more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.WorkoutRowLayout);
            name = itemView.findViewById(R.id.WorkoutRowTitle);
            desc = itemView.findViewById(R.id.WorkoutRowDescription);
            more = itemView.findViewById(R.id.WorkoutRowMore);
        }
    }

    private static final DiffUtil.ItemCallback<Routine> DIFF_CALLBACK = new DiffUtil.ItemCallback<Routine>() {
        @Override
        public boolean areItemsTheSame(@NonNull Routine oldItem, @NonNull Routine newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Routine oldItem, @NonNull Routine newItem) {
            return oldItem.equals(newItem);
        }
    };
}
