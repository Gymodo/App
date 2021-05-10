package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.FoodAdapter;
import com.github.gymodo.food.Food;
import com.github.gymodo.food.MealType;
import com.github.gymodo.fragments.WorkoutDetailFragment;
import com.github.gymodo.viewmodels.AddDietViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDietFragment extends Fragment {

    TextInputEditText inputName;
    TextInputEditText inputDesc;

    RecyclerView listDinner;
    RecyclerView listLaunch;
    RecyclerView listBreakfast;
    RecyclerView listSnack;

    FoodAdapter dinnerAdapter;
    FoodAdapter launchAdapter;
    FoodAdapter breakfastAdapter;
    FoodAdapter snackAdapter;

    Button btnAddFoodBreakfast;
    Button btnAddFoodLaunch;
    Button btnAddFoodSnack;
    Button btnAddFoodDinner;

    private AddDietViewModel addDietViewModel;

    public CreateDietFragment() {
        // Required empty public constructor
    }

    public static CreateDietFragment newInstance() {
        CreateDietFragment fragment = new CreateDietFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_diet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputName = view.findViewById(R.id.AddDietTextInputName);
        inputDesc = view.findViewById(R.id.AddDietTextInputDescription);

        listDinner = view.findViewById(R.id.AddDietDinnerRecyclerView);
        dinnerAdapter = new FoodAdapter();
        listDinner.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listDinner.setAdapter(dinnerAdapter);

        listLaunch = view.findViewById(R.id.AddDietLaunchRecyclerView);
        launchAdapter = new FoodAdapter();
        listLaunch.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listLaunch.setAdapter(launchAdapter);

        listBreakfast = view.findViewById(R.id.AddDietBreakFastRecyclerView);
        breakfastAdapter = new FoodAdapter();
        listBreakfast.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listBreakfast.setAdapter(breakfastAdapter);

        listSnack = view.findViewById(R.id.AddDietSnackRecyclerView);
        snackAdapter = new FoodAdapter();
        listSnack.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listSnack.setAdapter(snackAdapter);

        btnAddFoodBreakfast = view.findViewById(R.id.AddDietBreakfastButton);
        btnAddFoodLaunch = view.findViewById(R.id.AddDietLaunchButton);
        btnAddFoodSnack = view.findViewById(R.id.AddDietSnackButton);
        btnAddFoodDinner = view.findViewById(R.id.AddDietDinnerButton);

        addDietViewModel = new ViewModelProvider(requireActivity()).get("AddDietViewModel", AddDietViewModel.class);

        btnAddFoodBreakfast.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AddFoodFragment.ARG_MEALTYPE, MealType.BREAKFAST);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment, bundle);
        });

        btnAddFoodLaunch.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AddFoodFragment.ARG_MEALTYPE, MealType.LAUNCH);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment, bundle);
        });

        btnAddFoodSnack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AddFoodFragment.ARG_MEALTYPE, MealType.SNACK);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment, bundle);
        });

        btnAddFoodDinner.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AddFoodFragment.ARG_MEALTYPE, MealType.DINNER);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment, bundle);
        });

        addDietViewModel.getBreakfast().observe(getViewLifecycleOwner(), food -> {
            if(food == null)
                return;
            // add food to list
            List<Food> list = new ArrayList<>(breakfastAdapter.getCurrentList());
            list.add(food);
            breakfastAdapter.submitList(list);
        });
        addDietViewModel.getDinner().observe(getViewLifecycleOwner(), food -> {
            if(food == null)
                return;
            // add food to list
            List<Food> list = new ArrayList<>(dinnerAdapter.getCurrentList());
            list.add(food);
            dinnerAdapter.submitList(list);
        });
        addDietViewModel.getLaunch().observe(getViewLifecycleOwner(), food -> {
            if(food == null)
                return;
            // add food to list
            List<Food> list = new ArrayList<>(launchAdapter.getCurrentList());
            list.add(food);
            launchAdapter.submitList(list);
        });
        addDietViewModel.getSnack().observe(getViewLifecycleOwner(), food -> {
            if(food == null)
                return;
            // add food to list
            List<Food> list = new ArrayList<>(snackAdapter.getCurrentList());
            list.add(food);
            snackAdapter.submitList(list);
        });
    }
}