package com.github.gymodo.food;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a meal
 * @see Food
 * @see MealType
 * @see Diet
 */
public class Meal {
    /**
     * The meal type.
     */
    private MealType mealType;
    /**
     * The list of food in this meal.
     */
    private List<Food> foodList;

    public Meal() {
        this.foodList = new ArrayList<>();
    }

    public Meal(MealType mealType, List<Food> foodList) {
        this.mealType = mealType;
        this.foodList = foodList;
    }

    /**
     * Gets the meal type.
     *
     * @return The meal type.
     * @see #setMealType(MealType mealType)
     */
    public MealType getMealType() {
        return mealType;
    }

    /**
     * Sets the meal type.
     *
     * @param mealType The meal type.
     * @return this
     * @see #getMealType()
     */
    public Meal setMealType(MealType mealType) {
        this.mealType = mealType;
        return this;
    }

    /**
     * Gets the food list
     *
     * @return The food list.
     */
    public List<Food> getFoodList() {
        return foodList;
    }

    /**
     * Sets the food list.
     *
     * @param foodList The food list.
     * @return this
     */
    public Meal setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        return this;
    }

    /**
     * Calculates the total calories of this meal.
     *
     * @return The total calories.
     */
    public int getTotalCalories() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getCalories();
        }
        return total;
    }

    /**
     * Calculates the total fat of this meal.
     *
     * @return The total fat.
     */
    public int getTotalFat() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getTotalFat();
        }
        return total;
    }

    /**
     * Calculates the total sodium of this meal.
     *
     * @return The total sodium.
     */
    public int getTotalSodium() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getSodium();
        }
        return total;
    }

    /**
     * Calculates the total carbohydrates of this meal.
     *
     * @return The total carbohydrates.
     */
    public int getTotalCarboHydrates() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getTotalCarboHydrate();
        }
        return total;
    }

    /**
     * Calculates the total cholesterol of this meal.
     *
     * @return The total cholesterol.
     */
    public int getTotalCholesterol() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getCholesterol();
        }
        return total;
    }

    /**
     * Calculates the total protein of this meal.
     *
     * @return The total protein.
     */
    public int getTotalProtein() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getProtein();
        }
        return total;
    }
}
