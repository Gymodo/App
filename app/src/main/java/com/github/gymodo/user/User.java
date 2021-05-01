package com.github.gymodo.user;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.food.Diet;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
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
    private UserGoal goal;
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
     * Get user goal
     *
     * @return UserGoal
     */
    public UserGoal getGoal() {
        return goal;
    }

    /**
     * Set UserGoal
     *
     * @param goal
     * @return this
     */
    public User setGoal(UserGoal goal) {
        this.goal = goal;
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

    @Exclude
    public Task<List<Routine>> getSavedRoutines() {
        return Routine.getWhereIdIn(savedRoutinesIds);
    }

    @Exclude
    public Task<List<Diet>> getSavedDiets() {
        return Diet.getWhereIdIn(savedDietsIds);
    }

    /**
     * Saves this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_USERS, this, User.class);
    }

    /**
     * Updates this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_USERS, id, this, User.class);
    }

    /**
     * Gets a user by id.
     *
     * @param id The id of the user.
     * @return A task with the User as result.
     */
    @Exclude
    public static Task<User> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_USERS, id, User.class);
    }

    /**
     * Gets a list of users by ids.
     *
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<User>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_USERS, ids, User.class);
    }

    /**
     * Get all the User.
     *
     * @return all the User
     */
    @Exclude
    public static Task<List<User>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_USERS, User.class);
    }
}
