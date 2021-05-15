package com.github.gymodo.fragments.base_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationBaseFragment extends Fragment {

    public ReservationBaseFragment() {
        // Required empty public constructor
    }

    public static ReservationBaseFragment newInstance() {
        ReservationBaseFragment fragment = new ReservationBaseFragment();
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
        View view =  inflater.inflate(R.layout.fragment_reservation_base, container, false);

        getActivity().setTitle("Reservation");

        return view;
    }
}