package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;
import com.github.gymodo.food.Food;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFoodFragment extends Fragment {


    public static ScanFoodFragment newInstance() {
        ScanFoodFragment fragment = new ScanFoodFragment();
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
        View view = inflater.inflate(R.layout.fragment_scan_food, container, false);

        /*
        NavController navController = Navigation.findNavController(view);
        navController.getPreviousBackStackEntry().getSavedStateHandle().getLiveData("scanData").postValue(new Food());
        navController.popBackStack();

         */

        return view;
    }
}