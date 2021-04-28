package com.github.gymodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.adapters.SeriesAdapter;
import com.github.gymodo.exercise.Serie;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesFragment extends Fragment {

    RecyclerView recyclerView;
    SeriesAdapter adapter;
    List<Serie> series;
    Button addButton;

    public SeriesFragment() {
        // Required empty public constructor
        series = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SeriesFragment.
     */
    public static SeriesFragment newInstance() {
        SeriesFragment fragment = new SeriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_series, container, false);

        addButton = view.findViewById(R.id.SeriesAddBtn);
        recyclerView = view.findViewById(R.id.SeriesRecyclerView);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();

        Serie.listByAuthor(userId).addOnSuccessListener(list -> {
            // don't use = here, need to have the same reference.
            series.addAll(list);
        }).addOnFailureListener(fail -> {
            Toast.makeText(getContext(), "Error loading series", Toast.LENGTH_SHORT).show();
        });

        adapter = new SeriesAdapter(series, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(btnView -> {

            // TODO: Fragment Navigation.
        });

        return view;
    }
}