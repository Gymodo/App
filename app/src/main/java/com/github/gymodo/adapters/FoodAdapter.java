package com.github.gymodo.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.databinding.DietFoodRowBinding;
import com.github.gymodo.food.Food;

/**
 * Adapter for food objects.
 */
public class FoodAdapter extends ListAdapter<Food, FoodAdapter.ViewHolder> {

    public FoodAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DietFoodRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.diet_food_row, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Food food = getItem(position);
        holder.binding.setFood(food);
        holder.binding.executePendingBindings();
    }

    private static final DiffUtil.ItemCallback<Food> DIFF_CALLBACK = new DiffUtil.ItemCallback<Food>() {
        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        DietFoodRowBinding binding;

        public ViewHolder(DietFoodRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
