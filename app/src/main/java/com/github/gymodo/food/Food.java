package com.github.gymodo.food;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a food.
 *
 * @see MealType
 * @see Diet
 * @see Meal
 */
public class Food implements Serializable {
    @DocumentId
    private String id;
    private String name;
    private String barcode;
    private String imageUrl;
    private double calories;
    private double totalFat;
    private double cholesterol;
    private double sodium;
    private double totalCarboHydrate;
    private double protein;

    public Food() {
        this.name = "";
    }

    public Food(String name, double calories, double totalFat, double cholesterol, double sodium, double totalCarboHydrate, double protein) {
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * The calories of this food.
     *
     * @return The calories.
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Set the calories of this food.
     *
     * @param calories The calories.
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Get the total fat of this food.
     *
     * @return The total fat.
     */
    public double getTotalFat() {
        return totalFat;
    }

    /**
     * Set the total fat.
     *
     * @param totalFat The total fat.
     */
    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    /**
     * Get the cholesterol of this food.
     *
     * @return The cholesterol.
     */
    public double getCholesterol() {
        return cholesterol;
    }

    /**
     * Set the cholesterol of this food.
     *
     * @param cholesterol The cholesterol
     */
    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    /**
     * Get the sodium of this food.
     *
     * @return this
     */
    public double getSodium() {
        return sodium;
    }

    /**
     * Set the sodium of this food.
     *
     * @param sodium The sodium.
     * @return this
     */
    public Food setSodium(double sodium) {
        this.sodium = sodium;
        return this;
    }

    /**
     * Get the total carbohydrates of this food.
     *
     * @return the total carbohydrates.
     */
    public double getTotalCarboHydrate() {
        return totalCarboHydrate;
    }

    /**
     * Set the total carbohydrates.
     *
     * @param totalCarboHydrate The total carbohydrates.
     */
    public void setTotalCarboHydrate(double totalCarboHydrate) {
        this.totalCarboHydrate = totalCarboHydrate;
    }

    /**
     * Get the protein quantity of this food.
     *
     * @return The protein quantity.
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Set the protein quantity of this food.
     *
     * @param protein The protein quantity.
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Saves this object on the database
     * @return A task with the inserted id.
     */
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_FOODS, this, Food.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_FOODS, id, this, Food.class);
    }

    /**
     * Gets a Food by id.
     *
     * @param id The id of the Food.
     * @return A task with the Food as result.
     */
    public static Task<Food> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_FOODS, id, Food.class);
    }

    /**
     * Gets a list of Food by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    public static Task<List<Food>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_FOODS, ids, Food.class);
    }

    @BindingAdapter("imageUrl")
    @Exclude
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get().load(url).fit().centerCrop().into(imageView);
    }
}
