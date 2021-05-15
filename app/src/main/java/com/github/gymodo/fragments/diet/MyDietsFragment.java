package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.DietAdapter;
import com.github.gymodo.food.Diet;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    List<Diet> diets;
    DietAdapter dietAdapter;

    public MyDietsFragment() {
        // Required empty public constructor
    }

    public static MyDietsFragment newInstance() {
        MyDietsFragment fragment = new MyDietsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        diets = new ArrayList<>();
        dietAdapter = new DietAdapter();

        getParentFragmentManager().setFragmentResultListener("addedDiet", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Diet diet = (Diet) result.getSerializable("addedDiet");
                diets.add(diet);
                dietAdapter.submitList(diets);
            }
        });
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

        dietList.setLayoutManager(new LinearLayoutManager(getContext()));
        dietList.setAdapter(dietAdapter);



        addDiet.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_myDietsFragment_to_createDietFragment);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Diet.listAll().addOnSuccessListener(list -> {
            Toast.makeText(getContext(), "Loaded diets: " + list.size(), Toast.LENGTH_SHORT).show();
            diets.clear();
            diets.addAll(list);
            dietAdapter.submitList(diets);
        });
    }
}