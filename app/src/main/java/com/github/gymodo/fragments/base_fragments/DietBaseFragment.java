package com.github.gymodo.fragments.base_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietBaseFragment extends Fragment {

    public DietBaseFragment() {
        // Required empty public constructor
    }

    public static DietBaseFragment newInstance() {
        DietBaseFragment fragment = new DietBaseFragment();
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
        return inflater.inflate(R.layout.fragment_diet_base, container, false);
    }
}