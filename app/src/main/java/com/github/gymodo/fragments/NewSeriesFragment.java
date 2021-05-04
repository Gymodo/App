package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.gymodo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewSeriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSeriesFragment extends Fragment {
    Spinner exerciseSpinner;
    TextView exerciseDescription;
    TextInputLayout repsLayout;
    TextInputLayout weightLayout;
    TextInputEditText reps;
    TextInputEditText weight;
    FloatingActionButton addButton;
    Exercise selectedExercise;

    List<Exercise> availableExercises;

    public NewSeriesFragment() {
        // Required empty public constructor
        availableExercises = new ArrayList<>();
    }

    public static NewSeriesFragment newInstance(String routineId) {
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
        getActivity().setTitle("New Serie");

        View view = inflater.inflate(R.layout.fragment_new_series, container, false);

        exerciseSpinner = view.findViewById(R.id.NewSeriesExerciseSpinner);
        exerciseDescription = view.findViewById(R.id.NewSeriesExerciseDescription);
        reps = view.findViewById(R.id.NewSeriesReps);
        weight = view.findViewById(R.id.NewSeriesWeight);
        addButton = view.findViewById(R.id.NewSeriesAddButton);
        repsLayout = view.findViewById(R.id.NewSeriesRepsLayout);
        weightLayout = view.findViewById(R.id.NewSeriesWeightLayout);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);

        Exercise.listAll().addOnSuccessListener(exerciseList -> {
            availableExercises.addAll(exerciseList);
            arrayAdapter.addAll(availableExercises.stream().map(Exercise::getName).collect(Collectors.toList()));
        })
                .addOnFailureListener(fail -> {
                    Toast.makeText(getContext(), "Error fetching exercises.", Toast.LENGTH_SHORT).show();
                });

        exerciseSpinner.setAdapter(arrayAdapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExercise = availableExercises.get(position);
                exerciseDescription.setText(selectedExercise.getDescription());
                repsLayout.setVisibility(View.VISIBLE);
                weightLayout.setVisibility(View.VISIBLE);
                exerciseDescription.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedExercise = null;
                exerciseDescription.setText("");
                repsLayout.setVisibility(View.INVISIBLE);
                weightLayout.setVisibility(View.INVISIBLE);
                exerciseDescription.setVisibility(View.INVISIBLE);
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        addButton.setOnClickListener(btnView -> {
            if (selectedExercise == null) {
                Toast.makeText(getContext(), "Select an exercise first.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(reps.getText().toString().isEmpty()) {
                reps.setText("0");
            }

            if(weight.getText().toString().isEmpty()) {
                weight.setText("0");
            }

            Serie serie = new Serie();
            serie.setAuthorId(userId)
                    .setExerciseId(selectedExercise.getId())
                    .setReps(Integer.parseInt(reps.getText().toString()))
                    .setWeight(Integer.parseInt(weight.getText().toString()));

            serie.save().addOnSuccessListener(id -> {
                Log.d("newseries", "new id " + id);
                // Seria is saved, redirect somewhere.
                NavController navController = Navigation.findNavController(view);
                navController.getPreviousBackStackEntry().getSavedStateHandle().getLiveData("serieId").postValue(id);
                navController.popBackStack();
            });
        });
        return view;
    }
}