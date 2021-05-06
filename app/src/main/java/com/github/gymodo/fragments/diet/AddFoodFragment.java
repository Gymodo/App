package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.food.Food;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {

    TextInputEditText inputName;
    TextInputEditText inputCalories;
    TextInputEditText inputtotalFat;
    TextInputEditText inputCholesterol;
    TextInputEditText inputSodium;
    TextInputEditText inputCarbs;
    TextInputEditText inputProtein;
    Button addButton;
    Button scanButton;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFoodFragment newInstance(String param1, String param2) {
        AddFoodFragment fragment = new AddFoodFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        inputName = view.findViewById(R.id.AddFoodInputName);
        inputCalories = view.findViewById(R.id.AddFoodInputCalories);
        inputtotalFat = view.findViewById(R.id.AddFoodInputFat);
        inputCholesterol = view.findViewById(R.id.AddFoodInputCholesterol);
        inputSodium = view.findViewById(R.id.AddFoodInputSodium);
        inputCarbs = view.findViewById(R.id.AddFoodInputCarbs);
        inputProtein = view.findViewById(R.id.AddFoodInputProtein);
        addButton = view.findViewById(R.id.AddFoodAdd);
        scanButton = view.findViewById(R.id.AddFoodScan);

        scanButton.setOnClickListener(v -> {

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("scanData", new Food())
                .observe(getViewLifecycleOwner(), new Observer<Food>() {
                    @Override
                    public void onChanged(Food food) {
                        Log.d("addfood", "got food:" + food.toString());
                    }
                });
    }
}