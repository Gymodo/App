package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWorkoutFragment extends Fragment {
    FloatingActionButton addSerieBtn;

    public AddWorkoutFragment() {
        // Required empty public constructor
    }

    public static AddWorkoutFragment newInstance() {
        AddWorkoutFragment fragment = new AddWorkoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_workout, container, false);

        addSerieBtn = view.findViewById(R.id.AddSeriesToWorkoutBtn);

        addSerieBtn.setOnClickListener(btnView -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_addWorkoutFragment_to_newSeriesFragment);
        });

        return view;
    }
}