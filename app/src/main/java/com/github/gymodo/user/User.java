package com.github.gymodo.user;

import com.github.gymodo.Constants;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.food.Diet;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a user profile.
 */
public class User {
    @DocumentId
    private String id;
    private String name;
    private Date birthDate;
    private float weight;
    private boolean isAdmin;
    private List<String> savedDietsIds;
    private List<String> savedRoutinesIds;

    /**
     * Build nameless user
     */
    public User() {
        this.savedDietsIds = new ArrayList<>();
        this.savedRoutinesIds = new ArrayList<>();
    }

    /**
     * Build a user with the data received by parameter
     *
     * @param name             name
     * @param birthDate        birthDate
     * @param weight           weight
     * @param isAdmin          isAdmin
     * @param savedDietsIds    Diets
     * @param savedRoutinesIds Routine
     */
    public User(String name, Date birthDate, float weight, boolean isAdmin, List<String> savedDietsIds, List<String> savedRoutinesIds) {
        this.name = name;
        this.birthDate = birthDate;
        this.weight = weight;
        this.isAdmin = isAdmin;
        this.savedDietsIds = savedDietsIds;
        this.savedRoutinesIds = savedRoutinesIds;
    }

    /**
     * Returns user name
     *
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     *
     * @param name user name
     * @return this
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns birthdate
     *
     * @return birthdate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Set bithdate
     *
     * @param birthDate
     * @return this
     */
    public User setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * Returns weight
     *
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Set weight
     *
     * @param weight weight
     * @return this
     */
    public User setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Returns true if user is admin
     *
     * @return true if user is admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set admin
     *
     * @param admin true if user is admin
     * @return this
     */
    public User setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    /**
     * Returns diet list
     *
     * @return diet list
     */
    public List<String> getSavedDietsIds() {
        return savedDietsIds;
    }

    /**
     * Set diet list
     *
     * @param savedDietsIds dietList
     * @return this
     */
    public User setSavedDietsIds(List<String> savedDietsIds) {
        this.savedDietsIds = savedDietsIds;
        return this;
    }

    /**
     * Returns routine list
     *
     * @return routineList
     */
    public List<String> getSavedRoutinesIds() {
        return savedRoutinesIds;
    }

    /**
     * Set routine list
     *
     * @param savedRoutinesIds routineList
     * @return this
     */
    public User setSavedRoutinesIds(List<String> savedRoutinesIds) {
        this.savedRoutinesIds = savedRoutinesIds;
        return this;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public Task<List<Routine>> getSavedRoutines() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_ROUTINES)
                .whereIn(FieldPath.documentId(), savedRoutinesIds)
                .get()
                .continueWith(x ->
                        x.getResult()
                                .getDocuments()
                                .parallelStream()
                                .map(y -> y.toObject(Routine.class))
                                .collect(Collectors.toList())
                );
    }

    public Task<List<Diet>> getSavedDiets() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_DIETS)
                .whereIn(FieldPath.documentId(), savedRoutinesIds)
                .get()
                .continueWith(x ->
                        x.getResult()
                                .getDocuments()
                                .parallelStream()
                                .map(y -> y.toObject(Diet.class))
                                .collect(Collectors.toList())
                );
    }
}
