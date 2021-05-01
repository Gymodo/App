package com.github.gymodo.food;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import kotlin.NotImplementedError;

/**
 * Represents a diet
 *
 * @see Food
 * @see Meal
 * @see MealType
 */
public class Diet {
    @DocumentId
    private String id;
    private String dinnerId;
    private String breakfastId;
    private String launchId;
    private String snackId;
    private String authorId;

    public Diet() {
    }

    public Diet(String id, String dinnerId, String breakfastId, String launchId, String snackId, String authorId) {
        this.id = id;
        this.dinnerId = dinnerId;
        this.breakfastId = breakfastId;
        this.launchId = launchId;
        this.snackId = snackId;
        this.authorId = authorId;
    }

    /**
     * Returns the dinner.
     *
     * @return The dinner meal.
     */
    public String getDinnerId() {
        return dinnerId;
    }

    /**
     * Sets the dinner meal.
     *
     * @param dinnerId The dinner meal.
     * @return this
     */
    public Diet setDinnerId(String dinnerId) {
        this.dinnerId = dinnerId;
        return this;
    }

    /**
     * Returns the breakfast.
     *
     * @return The breakfast meal.
     */
    public String getBreakfastId() {
        return breakfastId;
    }

    /**
     * Sets the breakfast meal.
     *
     * @param breakfastId The breakfast meal.
     * @return this
     */
    public Diet setBreakfastId(String breakfastId) {
        this.breakfastId = breakfastId;
        return this;
    }

    /**
     * Returns the launch.
     *
     * @return The launch meal.
     */
    public String getLaunchId() {
        return launchId;
    }

    /**
     * Sets the launch meal.
     *
     * @param launchId The launch meal.
     * @return this
     */
    public Diet setLaunchId(String launchId) {
        this.launchId = launchId;
        return this;
    }

    /**
     * Returns the snack.
     *
     * @return The snack meal.
     */
    public String getSnackId() {
        return snackId;
    }

    /**
     * Sets the snack meal.
     *
     * @param snackId The snack meal.
     * @return this
     */
    public Diet setSnackId(String snackId) {
        this.snackId = snackId;
        return this;
    }

    @Exclude
    public Task<Meal> getLaunch() {
        return Meal.getByID(launchId);
    }

    @Exclude
    public Task<Meal> getBreakfast() {
        return Meal.getByID(breakfastId);
    }

    @Exclude
    public Task<Meal> getDinner() {
        return Meal.getByID(dinnerId);
    }

    @Exclude
    public Task<Meal> getSnack() {
        return Meal.getByID(snackId);
    }


    public String getId() {
        return id;
    }

    public Diet setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Diet setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }

    /**
     * Saves this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_DIETS, this, Diet.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_DIETS, id, this, Diet.class);
    }

    /**
     * Gets a Diet by id.
     *
     * @param id The id of the Diet.
     * @return A task with the Diet as result.
     */
    @Exclude
    public static Task<Diet> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_DIETS, id, Diet.class);
    }

    /**
     * Gets a list of Diet by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Diet>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_DIETS, ids, Diet.class);
    }
}
