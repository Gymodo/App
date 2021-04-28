package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gymodo.exercise.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewSeriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSeriesFragment extends Fragment {

    Spinner exerciseSpinner;
    TextView exerciseDescription;
    TextInputEditText reps;
    TextInputEditText weight;
    FloatingActionButton addButton;
    List<Exercise> exercises;

    public NewSeriesFragment() {
        // Required empty public constructor
        exercises = new ArrayList<>();
    }

    public static NewSeriesFragment newInstance() {
        NewSeriesFragment fragment = new NewSeriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_series, container, false);

        exerciseSpinner = view.findViewById(R.id.NewSeriesExerciseSpinner);
        exerciseDescription = view.findViewById(R.id.NewSeriesExerciseDescription);
        reps = view.findViewById(R.id.NewSeriesReps);
        weight = view.findViewById(R.id.NewSeriesWeight);
        addButton = view.findViewById(R.id.NewSeriesAddButton);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);

        Exercise.listAll().addOnSuccessListener(exerciseList -> {
            exercises.addAll(exerciseList);
            arrayAdapter.addAll(exercises.stream().map(Exercise::getName).collect(Collectors.toList()));
        })
        .addOnFailureListener(fail -> {
            Toast.makeText(getContext(), "Error fetching exercises.", Toast.LENGTH_SHORT).show();
        });

        exerciseSpinner.setAdapter(arrayAdapter);

        return view;
    }
}