package com.github.gymodo.user;

import com.github.gymodo.exercise.Routine;
import com.github.gymodo.food.Diet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a user profile.
 */
public class User {

    private String name;
    private Date birthDate;
    private float weight;
    private boolean isAdmin;
    private List<Diet> savedDiets;
    private List<Routine> savedRoutine;

    /**
     * Build nameless user
     */
    public User() {
        this.savedDiets = new ArrayList<>();
        this.savedRoutine = new ArrayList<>();
    }

    /**
     * Build a user with the data received by parameter
     *
     * @param name name
     * @param birthDate birthDate
     * @param weight weight
     * @param isAdmin isAdmin
     * @param savedDiets Diets
     * @param savedRoutine Routine
     */
    public User(String name, Date birthDate, float weight, boolean isAdmin, List<Diet> savedDiets, List<Routine> savedRoutine) {
        this.name = name;
        this.birthDate = birthDate;
        this.weight = weight;
        this.isAdmin = isAdmin;
        this.savedDiets = savedDiets;
        this.savedRoutine = savedRoutine;
    }

    /**
     * Returns user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     * @param name user name
     * @return this
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns birthdate
     * @return birthdate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Set bithdate
     * @param birthDate
     * @return this
     */
    public User setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * Returns weight
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Set weight
     * @param weight weight
     * @return this
     */
    public User setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Returns true if user is admin
     * @return true if user is admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set admin
     * @param admin true if user is admin
     * @return this
     */
    public User setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    /**
     * Returns diet list
     * @return diet list
     */
    public List<Diet> getSavedDiets() {
        return savedDiets;
    }

    /**
     * Set diet list
     * @param savedDiets dietList
     * @return this
     */
    public User setSavedDiets(List<Diet> savedDiets) {
        this.savedDiets = savedDiets;
        return this;
    }

    /**
     * Returns routine list
     * @return routineList
     */
    public List<Routine> getSavedRoutine() {
        return savedRoutine;
    }

    /**
     * Set routine list
     * @param savedRoutine routineList
     * @return this
     */
    public User setSavedRoutine(List<Routine> savedRoutine) {
        this.savedRoutine = savedRoutine;
        return this;
    }
}
