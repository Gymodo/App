package com.github.gymodo.food;

import com.github.gymodo.Constants;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
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
     * Calculates the total calories of this meal.
     *
     * @return The total calories.
     */
    public Task<Integer> getTotalCalories() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getCalories();
                    }
                    return total;
                });
    }

    /**
     * Calculates the total fat of this meal.
     *
     * @return The total fat.
     */
    public Task<Integer> getTotalFat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getTotalFat();
                    }
                    return total;
                });
    }

    /**
     * Calculates the total sodium of this meal.
     *
     * @return The total sodium.
     */
    public Task<Integer> getTotalSodium() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getSodium();
                    }
                    return total;
                });
    }

    /**
     * Calculates the total carbohydrates of this meal.
     *
     * @return The total carbohydrates.
     */
    public Task<Integer> getTotalCarboHydrates() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getTotalCarboHydrate();
                    }
                    return total;
                });
    }

    /**
     * Calculates the total cholesterol of this meal.
     *
     * @return The total cholesterol.
     */
    public Task<Integer> getTotalCholesterol() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getCholesterol();
                    }
                    return total;
                });
    }

    /**
     * Calculates the total protein of this meal.
     *
     * @return The total protein.
     */
    public Task<Integer> getTotalProtein() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    int total = 0;
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        total += food.getProtein();
                    }
                    return total;
                });
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
     * @return A task with a list of foods.
     */
    public Task<List<Food>> getFoods() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_FOODS).whereIn(FieldPath.documentId(), foodListIds)
                .get()
                .continueWith(task -> {
                    List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    return docs.parallelStream().map(x -> x.toObject(Food.class)).collect(Collectors.toList());
                });
    }

    public Task<User> getAuthor() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_USERS)
                .document(authorId)
                .get()
                .continueWith(x -> x.getResult().toObject(User.class));
    }
}
