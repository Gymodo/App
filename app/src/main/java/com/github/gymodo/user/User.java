package com.github.gymodo.user;

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

    public User() {
        this.savedDiets = new ArrayList<>();
    }

    public User(String name, Date birthDate, float weight, boolean isAdmin, List<Diet> savedDiets) {
        this.name = name;
        this.birthDate = birthDate;
        this.weight = weight;
        this.isAdmin = isAdmin;
        this.savedDiets = savedDiets;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public User setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public User setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public List<Diet> getSavedDiets() {
        return savedDiets;
    }

    public User setSavedDiets(List<Diet> savedDiets) {
        this.savedDiets = savedDiets;
        return this;
    }
}
