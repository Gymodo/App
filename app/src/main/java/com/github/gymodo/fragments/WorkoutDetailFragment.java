package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

    private static final String ARG_ROUTINE_ID = "ARG_ROUTINE_ID";

    private String routineId;
    RecyclerView recyclerView;
    SeriesAdapter seriesAdapter;
    TextView name;
    TextView description;
    FloatingActionButton addSerieButton;

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

                // TODO do something
                throw new UnsupportedOperationException("falta implementar");
            });
        }

        return view;
    }
}