package com.github.gymodo.exercise;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.List;

/**
 * Represents a Exercise
 *
 * @see Muscle
 * @see Exercise
 * @see Routine
 */
public class Serie {
    @DocumentId
    private String id;
    private int reps;
    private int weight;
    private String exerciseId;
    private String authorId;

    /**
     * Build nameless exercise
     */
    public Serie() {
    }

    /**
     * Build an serie with the data received by parameter
     *
     * @param reps       Repetitions
     * @param weight     Weight
     * @param exerciseId exerciseId
     */
    public Serie(int reps, int weight, String exerciseId, String authorId) {
        this.reps = reps;
        this.weight = weight;
        this.exerciseId = exerciseId;
        this.authorId = authorId;
    }

    /**
     * Returns the number of repetitions
     *
     * @return reps
     */
    public int getReps() {
        return reps;
    }

    /**
     * Set the number of reps
     *
     * @param reps reps
     * @return this
     */
    public Serie setReps(int reps) {
        this.reps = reps;
        return this;
    }

    /**
     * Returns rep weight
     *
     * @return rep weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the weight for rep
     *
     * @param weight weight
     * @return this
     */
    public Serie setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Returns the calculated volume.
     * @return The volume.
     */
    public int getVolume() {
        return getWeight() * getReps();
    }

    /**
     * Returns Exercise
     *
     * @return Exercise
     */
    public String getExerciseId() {
        return exerciseId;
    }

    /**
     * Set exerciseId to serie
     *
     * @param exerciseId exercise
     * @return this
     */
    public Serie setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Serie setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getId() {
        return id;
    }

    public Serie setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Saves this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_SERIES, this, Serie.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_SERIES, id, this, Serie.class);
    }

    /**
     * Gets a Serie by id.
     *
     * @param id The id of the Serie.
     * @return A task with the Serie as result.
     */
    @Exclude
    public static Task<Serie> get(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_SERIES, id, Serie.class);
    }

    /**
     * List all series made by author.
     * @param authorId The user id.
     * @return A list of series
     */
    @Exclude
    public static Task<List<Serie>> listByAuthor(String authorId) {
        return DatabaseUtil.getWhereValueIs(Constants.COLLECTION_SERIES, "authorId", authorId, Serie.class);
    }

    /**
     * Get all the series.
     * @return all the series
     */
    @Exclude
    public static Task<List<Serie>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_SERIES, Serie.class);
    }

    /**
     * Gets a Serie by id.
     *
     * @param id The id of the Serie.
     * @return A task with the Serie as result.
     */
    @Exclude
    public static Task<Serie> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_SERIES, id, Serie.class);
    }

    /**
     * Gets a list of Serie by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Serie>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_SERIES, ids, Serie.class);
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }
}
