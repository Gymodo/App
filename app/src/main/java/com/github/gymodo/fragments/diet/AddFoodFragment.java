package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.databinding.FragmentAddFoodBinding;
import com.github.gymodo.food.Food;
import com.github.gymodo.food.MealType;
import com.github.gymodo.viewmodels.AddDietViewModel;
import com.github.gymodo.viewmodels.FoodViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {
    public static final String ARG_MEALTYPE = "ARG_MEALTYPE";

    Button addButton;
    Button scanButton;

    MealType mealType;

    FragmentAddFoodBinding binding;

    FoodViewModel foodViewModel;
    AddDietViewModel addDietViewModel;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    public static AddFoodFragment newInstance(MealType mealType) {
        AddFoodFragment fragment = new AddFoodFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEALTYPE, mealType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mealType = (MealType) getArguments().getSerializable(ARG_MEALTYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_food, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_add_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDietViewModel = new ViewModelProvider(requireActivity()).get("AddDietViewModel", AddDietViewModel.class);
        foodViewModel = new ViewModelProvider(requireActivity()).get("ScannedFood", FoodViewModel.class);
        binding.setViewmodel(foodViewModel);

        addButton = view.findViewById(R.id.AddFoodAdd);
        scanButton = view.findViewById(R.id.AddFoodScan);

        scanButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_addFoodFragment_to_scanFoodFragment);
        });

        addButton.setOnClickListener(v -> {
            if(binding.getViewmodel().getFood().getValue().getName().isEmpty())
                return;

            NavController navController = Navigation.findNavController(view);

            switch (mealType) {
                case BREAKFAST:
                    addDietViewModel.getBreakfast().setValue(binding.getViewmodel().getFood().getValue());
                    break;
                case LAUNCH:
                    addDietViewModel.getLaunch().setValue(binding.getViewmodel().getFood().getValue());
                    break;
                case DINNER:
                    addDietViewModel.getDinner().setValue(binding.getViewmodel().getFood().getValue());
                    break;
                case SNACK:
                    addDietViewModel.getSnack().setValue(binding.getViewmodel().getFood().getValue());
                    break;
            }

            foodViewModel.setFood(new Food());

            navController.popBackStack();
        });
    }
}