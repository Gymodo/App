package com.github.gymodo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SerieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SerieDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERIE_ID = "ARG_SERIE_ID";

    private String serieId;

    public SerieDetailFragment() {
        // Required empty public constructor
    }

    public static SerieDetailFragment newInstance(String serieId) {
        SerieDetailFragment fragment = new SerieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SERIE_ID, serieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serieId = getArguments().getString(ARG_SERIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serie_detail, container, false);
        return view;
    }
}