package com.github.gymodo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.gymodo.food.Food;

public class AddDietViewModel extends ViewModel {
    private final MutableLiveData<Food> dinner = new MutableLiveData<Food>();
    private final MutableLiveData<Food> launch = new MutableLiveData<Food>();
    private final MutableLiveData<Food> breakfast = new MutableLiveData<Food>();
    private final MutableLiveData<Food> snack = new MutableLiveData<Food>();

    public void  setDinner(Food food) {
        dinner.setValue(food);
    }

    public void  setLaunch(Food food) {
        launch.setValue(food);
    }

    public void  setBreakfast(Food food) {
        breakfast.setValue(food);
    }

    public void  setSnack(Food food) {
        snack.setValue(food);
    }

    public MutableLiveData<Food> getDinner() {
        return dinner;
    }

    public MutableLiveData<Food> getLaunch() {
        return launch;
    }

    public MutableLiveData<Food> getBreakfast() {
        return breakfast;
    }

    public MutableLiveData<Food> getSnack() {
        return snack;
    }
}
