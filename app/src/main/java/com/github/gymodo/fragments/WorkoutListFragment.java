package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.WorkoutAdapter;
import com.github.gymodo.exercise.Routine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutListFragment extends Fragment {
    RecyclerView recyclerView;
    WorkoutAdapter adapter;
    FloatingActionButton addBtn;

    public WorkoutListFragment() {
    }

    public static WorkoutListFragment newInstance() {
        WorkoutListFragment fragment = new WorkoutListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WorkoutAdapter(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        recyclerView = view.findViewById(R.id.WorkoutListRecycler);
        addBtn = view.findViewById(R.id.WorkoutListAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(btnView -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_workoutListFragment_to_addWorkoutFragment);

        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        Routine.listByAuthor(userId).addOnSuccessListener(routines -> {
            adapter.submitList(routines);
        }).addOnFailureListener(fail -> {
            Log.e("listByAuthor", fail.getLocalizedMessage());
            Toast.makeText(getContext(), "Error al cargar los workouts.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}