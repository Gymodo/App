package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.gymodo.R;
import com.google.android.material.textfield.TextInputEditText;

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

    Button btnAddFoodBreakfast;
    Button btnAddFoodLaunch;
    Button btnAddFoodSnack;
    Button btnAddFoodDinner;


    public CreateDietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDietFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDietFragment newInstance(String param1, String param2) {
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
        View view = inflater.inflate(R.layout.fragment_create_diet, container, false);

        inputName = view.findViewById(R.id.AddDietTextInputName);
        inputDesc = view.findViewById(R.id.AddDietTextInputDescription);

        listDinner = view.findViewById(R.id.AddDietDinnerRecyclerView);
        listLaunch = view.findViewById(R.id.AddDietLaunchRecyclerView);
        listBreakfast = view.findViewById(R.id.AddDietBreakFastRecyclerView);
        listSnack = view.findViewById(R.id.AddDietSnackRecyclerView);

        btnAddFoodBreakfast = view.findViewById(R.id.AddDietBreakfastButton);
        btnAddFoodLaunch = view.findViewById(R.id.AddDietLaunchButton);
        btnAddFoodSnack = view.findViewById(R.id.AddDietSnackButton);
        btnAddFoodDinner = view.findViewById(R.id.AddDietDinnerButton);

        btnAddFoodBreakfast.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment);
        });

        btnAddFoodLaunch.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment);
        });

        btnAddFoodSnack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment);
        });

        btnAddFoodDinner.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_createDietFragment_to_addFoodFragment);
        });


        return view;
    }
}