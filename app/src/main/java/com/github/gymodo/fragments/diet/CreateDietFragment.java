package com.github.gymodo.fragments.diet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.adapters.FoodAdapter;
import com.github.gymodo.food.Diet;
import com.github.gymodo.food.Food;
import com.github.gymodo.food.Meal;
import com.github.gymodo.food.MealType;
import com.github.gymodo.fragments.WorkoutDetailFragment;
import com.github.gymodo.viewmodels.AddDietViewModel;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateDietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDietFragment extends Fragment {
    Button addDiet;

    TextInputEditText inputName;
    TextInputEditText inputDesc;

    RecyclerView listDinner;
    RecyclerView listLaunch;
    RecyclerView listBreakfast;
    RecyclerView listSnack;

    FoodAdapter dinnerAdapter;
    FoodAdapter launchAdapter;
    FoodAdapter breakfastAdapter;
    FoodAdapter snackAdapter;

    Button btnAddFoodBreakfast;
    Button btnAddFoodLaunch;
    Button btnAddFoodSnack;
    Button btnAddFoodDinner;

    List<Food> breakfastList;
    List<Food> dinnerList;
    List<Food> launchList;
    List<Food> snackList;

    public CreateDietFragment() {
        // Required empty public constructor
    }

    public static CreateDietFragment newInstance() {
        CreateDietFragment fragment = new CreateDietFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void addFood(MealType mealType, Food food) {
        switch (mealType) {

            case BREAKFAST: {
                breakfastList.add(food);
                breakfastAdapter.submitList(breakfastList);
                break;
            }
            case DINNER: {
                dinnerList.add(food);
                dinnerAdapter.submitList(dinnerList);
                break;
            }
            case LAUNCH: {
                launchList.add(food);
                launchAdapter.submitList(launchList);
                break;
            }
            case SNACK: {
                snackList.add(food);
                snackAdapter.submitList(snackList);
                break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("foodData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                FoodMessage foodMessage = (FoodMessage) result.getSerializable("foodData");
                addFood(foodMessage.getMealType(), foodMessage.getFood());
            }
        });

        dinnerAdapter = new FoodAdapter();
        launchAdapter = new FoodAdapter();
        snackAdapter = new FoodAdapter();
        breakfastAdapter = new FoodAdapter();

        breakfastList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();
        launchList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_diet, container, false);
    }

    private List<Task<String>> saveFoodList(List<Food> list) {
        List<Task<String>> tasks = new ArrayList<>();

        tasks = list.parallelStream().map(f -> f.save().onSuccessTask(id -> {
            if (id != null) {
                f.setId(id);
                return Tasks.forResult(id);
            }
            return Tasks.forCanceled();
        })).collect(Collectors.toList());

        return tasks;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDiet = view.findViewById(R.id.AddDietCreateDietButton);

        inputName = view.findViewById(R.id.AddDietTextInputName);
        inputDesc = view.findViewById(R.id.AddDietTextInputDescription);

        listDinner = view.findViewById(R.id.AddDietDinnerRecyclerView);
        listDinner.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listDinner.setAdapter(dinnerAdapter);

        listLaunch = view.findViewById(R.id.AddDietLaunchRecyclerView);
        listLaunch.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listLaunch.setAdapter(launchAdapter);

        listBreakfast = view.findViewById(R.id.AddDietBreakFastRecyclerView);
        listBreakfast.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listBreakfast.setAdapter(breakfastAdapter);

        listSnack = view.findViewById(R.id.AddDietSnackRecyclerView);
        listSnack.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listSnack.setAdapter(snackAdapter);

        btnAddFoodBreakfast = view.findViewById(R.id.AddDietBreakfastButton);
        btnAddFoodLaunch = view.findViewById(R.id.AddDietLaunchButton);
        btnAddFoodSnack = view.findViewById(R.id.AddDietSnackButton);
        btnAddFoodDinner = view.findViewById(R.id.AddDietDinnerButton);

        btnAddFoodBreakfast.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ScanFoodFragment.ARG_MEALTYPE, MealType.BREAKFAST);
            navController.navigate(R.id.action_createDietFragment_to_scanFoodFragment, bundle);
        });

        btnAddFoodLaunch.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ScanFoodFragment.ARG_MEALTYPE, MealType.LAUNCH);
            navController.navigate(R.id.action_createDietFragment_to_scanFoodFragment, bundle);
        });

        btnAddFoodSnack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ScanFoodFragment.ARG_MEALTYPE, MealType.SNACK);
            navController.navigate(R.id.action_createDietFragment_to_scanFoodFragment, bundle);
        });

        btnAddFoodDinner.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ScanFoodFragment.ARG_MEALTYPE, MealType.DINNER);
            navController.navigate(R.id.action_createDietFragment_to_scanFoodFragment, bundle);
        });

        addDiet.setOnClickListener(v -> {
            List<Task<String>> tasks = new ArrayList<>();
            tasks.addAll(saveFoodList(breakfastList));
            tasks.addAll(saveFoodList(dinnerList));
            tasks.addAll(saveFoodList(snackList));
            tasks.addAll(saveFoodList(launchList));

            Tasks.whenAllComplete(tasks).addOnSuccessListener(x -> {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                Meal breakfastMeal = new Meal();
                breakfastMeal.setAuthorId(auth.getUid());
                breakfastMeal.setFoodListIds(breakfastList.parallelStream().map(Food::getId).collect(Collectors.toList()));
                breakfastMeal.setMealType(MealType.BREAKFAST);

                Meal dinnerMeal = new Meal();
                dinnerMeal.setAuthorId(auth.getUid());
                dinnerMeal.setFoodListIds(dinnerList.parallelStream().map(Food::getId).collect(Collectors.toList()));
                dinnerMeal.setMealType(MealType.BREAKFAST);

                Meal launchMeal = new Meal();
                launchMeal.setAuthorId(auth.getUid());
                launchMeal.setFoodListIds(launchList.parallelStream().map(Food::getId).collect(Collectors.toList()));
                launchMeal.setMealType(MealType.BREAKFAST);

                Meal snackMeal = new Meal();
                snackMeal.setAuthorId(auth.getUid());
                snackMeal.setFoodListIds(snackList.parallelStream().map(Food::getId).collect(Collectors.toList()));
                snackMeal.setMealType(MealType.BREAKFAST);

                List<Task<String>> mealTasks = new ArrayList<>();


                mealTasks.add(
                        snackMeal.save().onSuccessTask(mealId -> {
                            snackMeal.setId(mealId);
                            return Tasks.forResult(mealId);
                        })
                );

                mealTasks.add(
                        launchMeal.save().onSuccessTask(mealId -> {
                            launchMeal.setId(mealId);
                            return Tasks.forResult(mealId);
                        })
                );

                mealTasks.add(
                        dinnerMeal.save().onSuccessTask(mealId -> {
                            dinnerMeal.setId(mealId);
                            return Tasks.forResult(mealId);
                        })
                );

                mealTasks.add(
                        breakfastMeal.save().onSuccessTask(mealId -> {
                            breakfastMeal.setId(mealId);
                            return Tasks.forResult(mealId);
                        })
                );

                Tasks.whenAllComplete(mealTasks).addOnSuccessListener(ids -> {
                    Diet diet = new Diet();
                    diet.setAuthorId(auth.getUid());
                    diet.setBreakfastId(breakfastMeal.getId());
                    diet.setDinnerId(dinnerMeal.getId());
                    diet.setLaunchId(launchMeal.getId());
                    diet.setSnackId(snackMeal.getId());
                    diet.setName(inputName.getText().toString());
                    diet.setDescription(inputDesc.getText().toString());
                    diet.save().addOnSuccessListener(id -> {
                        Toast.makeText(getContext(), "Diet created", Toast.LENGTH_SHORT).show();
                        diet.setId(id);
                        // Send this id back?
                        Bundle result = new Bundle();
                        result.putSerializable("addedDiet", diet);
                        getParentFragmentManager().setFragmentResult("addedDiet", result);
                        NavController navController = Navigation.findNavController(getView());
                        navController.popBackStack();
                    });
                });
            });
        });
    }
}