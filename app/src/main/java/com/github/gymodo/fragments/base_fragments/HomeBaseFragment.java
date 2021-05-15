package com.github.gymodo.fragments.base_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;


public class HomeBaseFragment extends Fragment {

    public HomeBaseFragment() {
        // Required empty public constructor
    }


    public static HomeBaseFragment newInstance() {
        HomeBaseFragment fragment = new HomeBaseFragment();
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

        View view = inflater.inflate(R.layout.fragment_home_base, container, false);
        getActivity().setTitle("Home");

        /*
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,new HomeFragment());
        fragmentTransaction.commit();*/

        return view;
    }
}