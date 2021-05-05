package com.github.gymodo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Muscle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MuscleAdapter extends ListAdapter<Muscle, MuscleAdapter.Viewholder> {
    ArrayList<Boolean> selectedItems;

    public MuscleAdapter() {
        super(DIFF_CALLBACK);
        selectedItems = new ArrayList<>();
    }

    public List<Muscle> getSelectedItems() {
        List<Muscle> all = getCurrentList();
        List<Muscle> selected = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            if (isPositionSelected(i)) {
                selected.add(all.get(i));
            }
        }
        return selected;
    }


    public boolean isPositionSelected(int position) {
        return selectedItems.get(position);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_row, parent, false);
        return new MuscleAdapter.Viewholder(view);
    }

    @Override
    public void onCurrentListChanged(@NonNull List<Muscle> previousList, @NonNull List<Muscle> currentList) {
        super.onCurrentListChanged(previousList, currentList);
        selectedItems = new ArrayList<>(Collections.nCopies(currentList.size(), false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Muscle muscle = getItem(position);

        holder.name.setText(muscle.getName());

        holder.layout.setOnClickListener(v -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
        });

        holder.checkBox.setOnCheckedChangeListener((v, c) -> {
            if (selectedItems.get(position) != null) {
                selectedItems.set(position, c);
            }
        });
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        CardView layout;
        CheckBox checkBox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.MuscleRowName);
            checkBox = itemView.findViewById(R.id.MuscleRowSelected);
            layout = itemView.findViewById(R.id.MuscleRowLayout);
        }
    }

    private static final DiffUtil.ItemCallback<Muscle> DIFF_CALLBACK = new DiffUtil.ItemCallback<Muscle>() {
        @Override
        public boolean areItemsTheSame(@NonNull Muscle oldItem, @NonNull Muscle newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Muscle oldItem, @NonNull Muscle newItem) {
            return oldItem.equals(newItem);
        }
    };
}
