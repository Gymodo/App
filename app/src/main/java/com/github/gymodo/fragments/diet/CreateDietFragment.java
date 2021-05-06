package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDietFragment extends Fragment {



    public CreateDietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDietFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDietFragment newInstance(String param1, String param2) {
        CreateDietFragment fragment = new CreateDietFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_diet, container, false);
        return view;
    }
}