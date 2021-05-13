package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gymodo.R;
import com.github.gymodo.adapters.FoodAdapter;
import com.github.gymodo.food.Diet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietDetailFragment extends Fragment {

    public  static final String ARG_DIET_ID = "ARG_DIET_ID";
    String dietId;

    TextView textTitle;
    TextView textDesc;

    TextView totalKcalBreakfast;
    TextView totalKcalDinner;
    TextView totalKcalLaunch;
    TextView totalKcalSnack;

    RecyclerView breakfastList;
    RecyclerView dinnerList;
    RecyclerView launchList;
    RecyclerView snackList;

    FoodAdapter breakfastAdapter;
    FoodAdapter dinnerAdapter;
    FoodAdapter launchAdapter;
    FoodAdapter snackAdapter;

    public DietDetailFragment() {
        // Required empty public constructor
    }

    public static DietDetailFragment newInstance(String dietId) {
        DietDetailFragment fragment = new DietDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DIET_ID, dietId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dietId = getArguments().getString(ARG_DIET_ID);
        }

        breakfastAdapter = new FoodAdapter();
        dinnerAdapter = new FoodAdapter();
        launchAdapter = new FoodAdapter();
        snackAdapter = new FoodAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTitle = view.findViewById(R.id.DietDetailTextTitle);
        textDesc = view.findViewById(R.id.DietDetailDescription);

        totalKcalBreakfast = view.findViewById(R.id.DietDetailTextBreakfastCalories);
        totalKcalDinner = view.findViewById(R.id.DietDetailTextDinnerCalories);
        totalKcalLaunch = view.findViewById(R.id.DietDetailTextLunchCalories);
        totalKcalSnack = view.findViewById(R.id.DietDetailTextSnackCalories);

        breakfastList = view.findViewById(R.id.DietDetailBreakFastRecyclerView);
        dinnerList = view.findViewById(R.id.DietDetailDinnerRecyclerView);
        launchList = view.findViewById(R.id.DietDetailLaunchRecyclerView);
        snackList = view.findViewById(R.id.DietDetailSnackRecyclerView);

        breakfastList.setLayoutManager(new LinearLayoutManager(getContext()));
        breakfastList.setAdapter(breakfastAdapter);
        dinnerList.setLayoutManager(new LinearLayoutManager(getContext()));
        dinnerList.setAdapter(dinnerAdapter);
        launchList.setLayoutManager(new LinearLayoutManager(getContext()));
        launchList.setAdapter(launchAdapter);
        snackList.setLayoutManager(new LinearLayoutManager(getContext()));
        snackList.setAdapter(snackAdapter);

        Diet.getByID(dietId).addOnSuccessListener(diet -> {
            textTitle.setText(diet.getName());
            textDesc.setText(diet.getDescription());

            diet.getLaunch().addOnSuccessListener(meal -> {
                meal.getFoods().addOnSuccessListener(foods -> {
                    double total = foods.parallelStream().mapToDouble(x -> x.getCalories()).sum();
                    totalKcalLaunch.setText(String.format("Total: %.02f kcal", total));
                    launchAdapter.submitList(foods);
                });
            });

            diet.getBreakfast().addOnSuccessListener(meal -> {
                meal.getFoods().addOnSuccessListener(foods -> {
                    double total = foods.parallelStream().mapToDouble(x -> x.getCalories()).sum();
                    totalKcalBreakfast.setText(String.format("Total: %.02f kcal", total));
                    breakfastAdapter.submitList(foods);
                });
            });

            diet.getDinner().addOnSuccessListener(meal -> {
                meal.getFoods().addOnSuccessListener(foods -> {
                    double total = foods.parallelStream().mapToDouble(x -> x.getCalories()).sum();
                    totalKcalDinner.setText(String.format("Total: %.02f kcal", total));
                    dinnerAdapter.submitList(foods);
                });
            });

            diet.getSnack().addOnSuccessListener(meal -> {
                meal.getFoods().addOnSuccessListener(foods -> {
                    double total = foods.parallelStream().mapToDouble(x -> x.getCalories()).sum();
                    totalKcalSnack.setText(String.format("Total: %.02f kcal", total));
                    snackAdapter.submitList(foods);
                });
            });
        });
    }
}