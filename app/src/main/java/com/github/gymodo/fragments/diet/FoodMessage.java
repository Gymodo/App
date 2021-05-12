package com.github.gymodo.fragments.diet;

import com.github.gymodo.food.Food;
import com.github.gymodo.food.MealType;

import java.io.Serializable;

/**
 * Used for results in fragments.
 */
public class FoodMessage implements Serializable {
    Food food;
    MealType mealType;

    public FoodMessage() {
    }

    public FoodMessage(Food food, MealType mealType) {
        this.food = food;
        this.mealType = mealType;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
