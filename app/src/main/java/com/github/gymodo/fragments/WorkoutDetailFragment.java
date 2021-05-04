package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.SeriesAdapter;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.exercise.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutDetailFragment extends Fragment {

    public static final String ARG_ROUTINE_ID = "ARG_ROUTINE_ID";

    private String routineId;
    RecyclerView recyclerView;
    SeriesAdapter seriesAdapter;
    TextView name;
    TextView description;
    FloatingActionButton addSerieButton;
    Routine routine;

    public WorkoutDetailFragment() {
    }

    public static WorkoutDetailFragment newInstance(String routineId) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROUTINE_ID, routineId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            routineId = getArguments().getString(ARG_ROUTINE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        if (routineId != null) {
            recyclerView = view.findViewById(R.id.WorkoutDetailReycler);
            name = view.findViewById(R.id.WorkoutDetailName);
            description = view.findViewById(R.id.WorkoutDetailDescription);
            addSerieButton = view.findViewById(R.id.WorkoutDetailAddSerieButton);

            seriesAdapter = new SeriesAdapter();

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(seriesAdapter);

            Routine.getByID(routineId).addOnSuccessListener(routine -> {
                this.routine = routine;
                name.setText(routine.getName());
                description.setText(routine.getDescription());

                routine.getSeries().addOnSuccessListener(serieList -> {
                    seriesAdapter.submitList(serieList);
                }).addOnFailureListener(fail -> {
                    Log.e("getSeries", fail.getLocalizedMessage());
                    Toast.makeText(view.getContext(), "Error al cargar las series.", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(fail -> {
                Log.e("getByID", fail.getLocalizedMessage());
                Toast.makeText(view.getContext(), "Error al cargar el workout.", Toast.LENGTH_SHORT).show();
            });

            addSerieButton.setOnClickListener(btnView -> {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_workoutDetailFragment_to_newSeriesFragment);
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("serieId", "")
                .observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String id) {
                        Log.d("workoutdetail", "got serie id: " + id);
                        if (id != null && !id.isEmpty()) {
                            if (routine != null) {
                                routine.getSeriesIds().add(id);

                                // Update routine
                                routine.update().addOnCompleteListener(v -> {
                                    // Update the view with the added id.
                                    routine.getSeries().addOnSuccessListener(serieList -> {
                                        seriesAdapter.submitList(serieList);
                                        Toast.makeText(view.getContext(), "serie añadida", Toast.LENGTH_SHORT).show();
                                    }).addOnFailureListener(fail -> {
                                        Log.e("getSeries", fail.getLocalizedMessage());
                                        Toast.makeText(view.getContext(), "Error al cargar las series.", Toast.LENGTH_SHORT).show();
                                    });
                                });
                            }
                        }
                    }
                });
    }
}