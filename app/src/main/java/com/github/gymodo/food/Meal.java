package com.github.gymodo.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Represents a meal
 *
 * @see Food
 * @see MealType
 * @see Diet
 */
public class Meal {
    @DocumentId
    private String id;
    private String authorId;
    /**
     * The meal type.
     */
    private MealType mealType;
    /**
     * The list of food in this meal.
     */
    private List<String> foodListIds;

    public Meal() {
        this.foodListIds = new ArrayList<String>();
    }

    public Meal(String id, String authorId, MealType mealType, List<String> foodListIds) {
        this.id = id;
        this.authorId = authorId;
        this.mealType = mealType;
        this.foodListIds = foodListIds;
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
    public List<String> getFoodListIds() {
        return foodListIds;
    }

    /**
     * Sets the food list.
     *
     * @param foodListIds The food list.
     * @return this
     */
    public Meal setFoodListIds(List<String> foodListIds) {
        this.foodListIds = foodListIds;
        return this;
    }

    /**
     * Sums all the Integer values of a property.
     * Utility method to not repeat code.
     *
     * @param mapper The method to map the values
     * @return The total value.
     */
    @Exclude
    private Task<Double> getTotalFoodProperty(ToDoubleFunction<Food> mapper) {
        return DatabaseUtil.getMappedDoubleSumWhereIn(Constants.COLLECTION_FOODS, foodListIds, mapper, Food.class);
    }

    /**
     * Calculates the total calories of this meal.
     *
     * @return The total calories.
     */
    @Exclude
    public Task<Double> getTotalCalories() {
        return getTotalFoodProperty(Food::getCalories);
    }

    /**
     * Calculates the total fat of this meal.
     *
     * @return The total fat.
     */
    @Exclude
    public Task<Double> getTotalFat() {
        return getTotalFoodProperty(Food::getTotalFat);
    }

    /**
     * Calculates the total sodium of this meal.
     *
     * @return The total sodium.
     */
    @Exclude
    public Task<Double> getTotalSodium() {
        return getTotalFoodProperty(Food::getSodium);
    }

    /**
     * Calculates the total carbohydrates of this meal.
     *
     * @return The total carbohydrates.
     */
    @Exclude
    public Task<Double> getTotalCarboHydrates() {
        return getTotalFoodProperty(Food::getTotalCarboHydrate);
    }

    /**
     * Calculates the total cholesterol of this meal.
     *
     * @return The total cholesterol.
     */
    @Exclude
    public Task<Double> getTotalCholesterol() {
        return getTotalFoodProperty(Food::getCholesterol);
    }

    /**
     * Calculates the total protein of this meal.
     *
     * @return The total protein.
     */
    @Exclude
    public Task<Double> getTotalProtein() {
        return getTotalFoodProperty(Food::getProtein);
    }

    public String getId() {
        return id;
    }

    public Meal setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Meal setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    /**
     * Returns a task with a list of foods this meal has.
     *
     * @return A task with a list of foods.
     */
    @Exclude
    public Task<List<Food>> getFoods() {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_FOODS, foodListIds, Food.class);
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }

    /**
     * Saves this object on the database
     *
     * @return A task with the inserted id.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_MEALS, this, Meal.class);
    }

    /**
     * Updates this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_MEALS, id, this, Meal.class);
    }

    /**
     * Gets a Meal by id.
     *
     * @param id The id of the Meal.
     * @return A task with the Meal as result.
     */
    @Exclude
    public static Task<Meal> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_MEALS, id, Meal.class);
    }

    /**
     * Gets a list of Meal by ids.
     *
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Meal>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_MEALS, ids, Meal.class);
    }
}
