package com.github.gymodo.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.food.Diet;
import com.github.gymodo.fragments.diet.DietDetailFragment;

/**
 * Adapter for diet objects.
 */
public class DietAdapter extends ListAdapter<Diet, DietAdapter.ViewHolder> {

    public DietAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Diet diet = getItem(position);
        holder.textName.setText(diet.getName());
        holder.textDesc.setText(diet.getDescription());
        holder.layout.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(holder.layout);
            Bundle bundle = new Bundle();
            bundle.putString(DietDetailFragment.ARG_DIET_ID, diet.getId());
            navController.navigate(R.id.action_myDietsFragment_to_dietDetailFragment, bundle);
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textDesc;
        ImageButton bookmark;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDesc = itemView.findViewById(R.id.DietRowDescription);
            textName = itemView.findViewById(R.id.DietRowName);
            bookmark = itemView.findViewById(R.id.DietRowBookmarkButton);
            layout = itemView.findViewById(R.id.DietRowLayout);
        }
    }

    private static final DiffUtil.ItemCallback<Diet> DIFF_CALLBACK = new DiffUtil.ItemCallback<Diet>() {
        @Override
        public boolean areItemsTheSame(@NonNull Diet oldItem, @NonNull Diet newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Diet oldItem, @NonNull Diet newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };
}
