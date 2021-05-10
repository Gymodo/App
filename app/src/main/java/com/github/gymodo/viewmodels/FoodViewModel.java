package com.github.gymodo.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.gymodo.food.Food;

public class FoodViewModel extends ViewModel {
    private final MutableLiveData<Food> food = new MutableLiveData<>();

    public void  setFood(Food food) {
        this.food.setValue(food);
    }

    public LiveData<Food> getFood() {
        return food;
    }
}
