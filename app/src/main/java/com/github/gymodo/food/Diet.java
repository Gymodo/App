package com.github.gymodo.food;

import com.github.gymodo.Constants;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public Task<Meal> getLaunch() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_MEALS)
                .document(launchId)
                .get()
                .continueWith(task -> task.getResult().toObject(Meal.class));
    }

    public Task<Meal> getBreakfast() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_MEALS)
                .document(breakfastId)
                .get()
                .continueWith(task -> task.getResult().toObject(Meal.class));
    }

    public Task<Meal> getDinner() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_MEALS)
                .document(dinnerId)
                .get()
                .continueWith(task -> task.getResult().toObject(Meal.class));
    }

    public Task<Meal> getSnack() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_MEALS)
                .document(snackId)
                .get()
                .continueWith(task -> task.getResult().toObject(Meal.class));
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

    public Task<User> getAuthor() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_USERS)
                .document(authorId)
                .get()
                .continueWith(x -> x.getResult().toObject(User.class));
    }
}
