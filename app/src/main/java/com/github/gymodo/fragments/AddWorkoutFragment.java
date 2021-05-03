package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.SeriesAdapter;
import com.github.gymodo.adapters.SeriesSelectAdapter;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.exercise.Serie;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWorkoutFragment extends Fragment {
    ExtendedFloatingActionButton addSerieBtn;
    Button createWorkout;
    SeriesSelectAdapter adapterAvailable;
    SeriesAdapter adapterSelected;
    RecyclerView available;
    TextInputEditText textName;
    TextInputEditText textDesc;

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

        available = view.findViewById(R.id.AddWorkoutAvailableList);
        createWorkout = view.findViewById(R.id.AddWorkoutCreateButton);
        textName = view.findViewById(R.id.AddWorkoutNameInput);
        textDesc = view.findViewById(R.id.AddWorkoutDescInput);

        adapterAvailable = new SeriesSelectAdapter();
        adapterSelected = new SeriesAdapter();

        available.setLayoutManager(new LinearLayoutManager(view.getContext()));
        available.setAdapter(adapterAvailable);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        Serie.listByAuthor(userId).addOnSuccessListener(serieList -> {
            adapterAvailable.submitList(serieList);
            adapterSelected.submitList(new ArrayList<>());
        });

        // TODO: Add select series when selected.

        addSerieBtn = view.findViewById(R.id.AddSeriesToWorkoutBtn);

        addSerieBtn.setOnClickListener(btnView -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_addWorkoutFragment_to_newSeriesFragment);
        });

        createWorkout.setOnClickListener(v -> {
            List<Serie> allSeries = adapterAvailable.getCurrentList();
            List<Serie> selectedSeries = new ArrayList<>();

            for (int i = 0; i < allSeries.size(); i++) {
                if (adapterAvailable.isPositionSelected(i)) {
                    selectedSeries.add(allSeries.get(i));
                }
            }

            Routine routine = new Routine();
            routine.setSeriesIds(selectedSeries.parallelStream().map(Serie::getId).collect(Collectors.toList()));
            routine.setName(textName.getText().toString());
            routine.setDescription(textDesc.getText().toString());
            routine.setAuthorId(userId);
            routine.save().addOnSuccessListener(vv -> {
                Toast.makeText(getContext(), "Workout created.", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            });
        });

        return view;
    }
}