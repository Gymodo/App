package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;
import com.github.gymodo.adapters.SeriesAdapter;
import com.github.gymodo.exercise.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWorkoutFragment extends Fragment {
    FloatingActionButton addSerieBtn;
    SeriesAdapter adapterAvailable;
    SeriesAdapter adapterSelected;
    RecyclerView available;
    RecyclerView selected;
    List<Serie> seriesAvailable;
    List<Serie> seriesSelected;
    TextInputEditText textName;
    TextInputEditText textDesc;

    public AddWorkoutFragment() {
        // Required empty public constructor
        seriesAvailable = new ArrayList<>();
        seriesSelected = new ArrayList<>();
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
        selected = view.findViewById(R.id.AddWorkoutSelectedList);
        textName = view.findViewById(R.id.AddWorkoutNameInput);
        textDesc = view.findViewById(R.id.AddWorkoutDescInput);

        adapterAvailable = new SeriesAdapter(seriesAvailable, view.getContext());
        adapterSelected = new SeriesAdapter(seriesSelected, view.getContext());

        available.setLayoutManager(new LinearLayoutManager(view.getContext()));
        available.setAdapter(adapterAvailable);

        selected.setLayoutManager(new LinearLayoutManager(view.getContext()));
        selected.setAdapter(adapterSelected);



        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        Serie.listByAuthor(userId).addOnSuccessListener(serieList -> {
            seriesAvailable.clear();
            seriesSelected.clear();
            seriesAvailable.addAll(serieList);
            adapterAvailable.notifyDataSetChanged();
        });

        // TODO: Add select series when selected.

        addSerieBtn = view.findViewById(R.id.AddSeriesToWorkoutBtn);

        addSerieBtn.setOnClickListener(btnView -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_addWorkoutFragment_to_newSeriesFragment);
        });

        return view;
    }
}