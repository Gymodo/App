package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    List<Routine> workouts;
    RecyclerView recyclerView;
    WorkoutAdapter adapter;
    FloatingActionButton addBtn;

    public WorkoutListFragment() {
        // Required empty public constructor
        workouts = new ArrayList<>();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Workouts");
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        recyclerView = view.findViewById(R.id.WorkoutListRecycler);
        addBtn = view.findViewById(R.id.WorkoutListAdd);

        adapter = new WorkoutAdapter(workouts, view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        Routine.listByAuthor(userId).addOnSuccessListener(routines -> {
            workouts.addAll(routines);
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(fail -> {
            Log.e("listByAuthor", fail.getLocalizedMessage());
            Toast.makeText(view.getContext(), "Error al cargar los workouts.", Toast.LENGTH_SHORT).show();
        });

        addBtn.setOnClickListener(btnView -> {
            // TODO do something
            throw new UnsupportedOperationException("falta implementar");
        });

        return view;
    }
}