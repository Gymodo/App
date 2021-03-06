package com.github.gymodo.exercise;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents a Exercise
 *
 * @see Muscle
 * @see Serie
 * @see Routine
 */
public class Exercise {
    @DocumentId
    private String id;
    private String name;
    private String description;
    // Muscle id list
    private List<String> muscleIds;

    /**
     * Build nameless exercise
     */
    public Exercise() {
        this.muscleIds = new ArrayList<>();
    }

    /**
     * Build an exercise with the data received by parameter
     *
     * @param name        Exercise name
     * @param description Exercise description
     * @param muscles     Muscle targets
     */
    public Exercise(String name, String description, List<String> muscles) {
        this.name = name;
        this.description = description;
        this.muscleIds = muscles;
    }

    /**
     * Returns exercise name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the exercise
     *
     * @param name name
     * @return name
     */
    public Exercise setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns exercise description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the exercise
     *
     * @param description
     * @return description
     */
    public Exercise setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Returns a list of muscles
     *
     * @return muscleList
     */
    public List<String> getMuscleIds() {
        return muscleIds;
    }

    /**
     * Set muscle list
     *
     * @param muscleIds muscleList
     * @return this
     */
    public Exercise setMuscleIds(List<String> muscleIds) {
        this.muscleIds = muscleIds;
        return this;
    }

    @Exclude
    public Task<List<Muscle>> getMuscles() {
        return Muscle.getWhereIdIn(muscleIds);
    }

    public String getId() {
        return id;
    }

    public Exercise setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Saves this object on the database
     * @return A task with the inserted object id.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_EXERCISES, this, Exercise.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_EXERCISES, id, this, Exercise.class);
    }

    /**
     * Gets a Exercise by id.
     *
     * @param id The id of the Exercise.
     * @return A task with the Exercise as result.
     */
    @Exclude
    public static Task<Exercise> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_EXERCISES, id, Exercise.class);
    }

    /**
     * Gets a list of Exercise by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Exercise>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_EXERCISES, ids, Exercise.class);
    }

    /**
     * Get all the Exercise.
     * @return all the Exercise
     */
    @Exclude
    public static Task<List<Exercise>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_EXERCISES, Exercise.class);
    }
}
