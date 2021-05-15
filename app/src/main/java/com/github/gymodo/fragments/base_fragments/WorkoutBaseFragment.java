package com.github.gymodo.fragments.base_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutBaseFragment extends Fragment {

    public WorkoutBaseFragment() {
        // Required empty public constructor
    }

    public static WorkoutBaseFragment newInstance() {
        WorkoutBaseFragment fragment = new WorkoutBaseFragment();
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
        getActivity().setTitle("Workouts");
        return inflater.inflate(R.layout.fragment_workout_base, container, false);
    }
}