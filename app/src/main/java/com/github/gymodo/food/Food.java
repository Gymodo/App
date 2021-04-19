package com.github.gymodo.food;

/**
 * Represents a food.
 *
 * @see MealType
 * @see Diet
 * @see Meal
 */
public class Food {
    private String name;
    private int calories;
    private int totalFat;
    private int cholesterol;
    private int sodium;
    private int totalCarboHydrate;
    private int protein;

    public Food() {
    }

    public Food(String name, int calories, int totalFat, int cholesterol, int sodium, int totalCarboHydrate, int protein) {
        this.name = name;
        this.calories = calories;
        this.totalFat = totalFat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.totalCarboHydrate = totalCarboHydrate;
        this.protein = protein;
    }

    /**
     * The name of this food.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this food
     *
     * @param name The name of the food.
     * @return this
     */
    public Food setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The calories of this food.
     *
     * @return The calories.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Set the calories of this food.
     *
     * @param calories The calories.
     * @return this
     */
    public Food setCalories(int calories) {
        this.calories = calories;
        return this;
    }

    /**
     * Get the total fat of this food.
     *
     * @return The total fat.
     */
    public int getTotalFat() {
        return totalFat;
    }

    /**
     * Set the total fat.
     *
     * @param totalFat The total fat.
     * @return this
     */
    public Food setTotalFat(int totalFat) {
        this.totalFat = totalFat;
        return this;
    }

    /**
     * Get the cholesterol of this food.
     *
     * @return The cholesterol.
     */
    public int getCholesterol() {
        return cholesterol;
    }

    /**
     * Set the cholesterol of this food.
     *
     * @param cholesterol The cholesterol
     * @return this
     */
    public Food setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
        return this;
    }

    /**
     * Get the sodium of this food.
     *
     * @return this
     */
    public int getSodium() {
        return sodium;
    }

    /**
     * Set the sodium of this food.
     *
     * @param sodium The sodium.
     * @return this
     */
    public Food setSodium(int sodium) {
        this.sodium = sodium;
        return this;
    }

    /**
     * Get the total carbohydrates of this food.
     *
     * @return the total carbohydrates.
     */
    public int getTotalCarboHydrate() {
        return totalCarboHydrate;
    }

    /**
     * Set the total carbohydrates.
     *
     * @param totalCarboHydrate The total carbohydrates.
     * @return this
     */
    public Food setTotalCarboHydrate(int totalCarboHydrate) {
        this.totalCarboHydrate = totalCarboHydrate;
        return this;
    }

    /**
     * Get the protein quantity of this food.
     *
     * @return The protein quantity.
     */
    public int getProtein() {
        return protein;
    }

    /**
     * Set the protein quantity of this food.
     *
     * @param protein The protein quantity.
     * @return this
     */
    public Food setProtein(int protein) {
        this.protein = protein;
        return this;
    }
}