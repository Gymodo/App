package com.github.gymodo.food;

import com.github.gymodo.user.User;

/**
 * Represents a diet
 *
 * @see Food
 * @see Meal
 * @see MealType
 */
public class Diet {
    private Meal dinner;
    private Meal breakfast;
    private Meal launch;
    private Meal snack;
    private User author;

    public Diet() {
    }

    public Diet(Meal dinner, Meal breakfast, Meal launch, Meal snack, User author) {
        this.dinner = dinner;
        this.breakfast = breakfast;
        this.launch = launch;
        this.snack = snack;
        this.author = author;
    }

    /**
     * Returns the dinner.
     *
     * @return The dinner meal.
     */
    public Meal getDinner() {
        return dinner;
    }

    /**
     * Sets the dinner meal.
     *
     * @param dinner The dinner meal.
     * @return this
     */
    public Diet setDinner(Meal dinner) {
        this.dinner = dinner;
        return this;
    }

    /**
     * Returns the breakfast.
     *
     * @return The breakfast meal.
     */
    public Meal getBreakfast() {
        return breakfast;
    }

    /**
     * Sets the breakfast meal.
     *
     * @param breakfast The breakfast meal.
     * @return this
     */
    public Diet setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
        return this;
    }

    /**
     * Returns the launch.
     *
     * @return The launch meal.
     */
    public Meal getLaunch() {
        return launch;
    }

    /**
     * Sets the launch meal.
     *
     * @param launch The launch meal.
     * @return this
     */
    public Diet setLaunch(Meal launch) {
        this.launch = launch;
        return this;
    }

    /**
     * Returns the snack.
     *
     * @return The snack meal.
     */
    public Meal getSnack() {
        return snack;
    }

    /**
     * Sets the snack meal.
     *
     * @param snack The snack meal.
     * @return this
     */
    public Diet setSnack(Meal snack) {
        this.snack = snack;
        return this;
    }

    /**
     * Returns the author of this diet.
     *
     * @return The author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author The author.
     * @return this
     */
    public Diet setAuthor(User author) {
        this.author = author;
        return this;
    }

    /**
     * Returns the total calories in a day of this diet.
     * @return The total calories
     */
    public int getTotalCalories() {
        int total = 0;

        if(launch != null)
            total += launch.getTotalCalories();

        if(dinner != null)
            total += dinner.getTotalCalories();

        if(breakfast != null)
            total += breakfast.getTotalCalories();

        if(snack != null)
            total += snack.getTotalCalories();

        return total;
    }
}
