package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.gymodo.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDietsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDietsFragment extends Fragment {

    ImageButton btnSort;
    Chip btnMyDiets;
    Chip btnExplore;
    RecyclerView dietList;
    FloatingActionButton addDiet;

    public MyDietsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicDietsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDietsFragment newInstance(String param1, String param2) {
        MyDietsFragment fragment = new MyDietsFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_diets, container, false);

        btnMyDiets = view.findViewById(R.id.MyDietsButtonMy);
        btnExplore = view.findViewById(R.id.MyDietsButtonExplore);
        btnSort = view.findViewById(R.id.MyDietsSortButton);
        dietList = view.findViewById(R.id.MyDietsListRecyclerView);
        addDiet = view.findViewById(R.id.MyDietAddButton);

        addDiet.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_myDietsFragment_to_createDietFragment);
        });

        return view;
    }
}