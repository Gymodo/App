package com.github.gymodo.exercise;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.food.Meal;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a Routine
 *
 * @see Muscle
 * @see Serie
 * @see Exercise
 */
public class Routine {
    @DocumentId
    private String id;
    private String name;
    private String description;
    private String authorId;
    private List<String> seriesIds;


    /**
     * Build nameless routine
     */
    public Routine() {
        this.seriesIds = new ArrayList<String>();
    }

    /**
     * Build an routine with the data received by parameter
     *
     * @param name      name
     * @param seriesIds series
     */
    public Routine(String name, String authorId, List<String> seriesIds) {
        this.name = name;
        this.authorId = authorId;
        this.seriesIds = seriesIds;
    }

    /**
     * Returns routine name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the routine
     *
     * @param name name
     * @return this
     */
    public Routine setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a list of series
     *
     * @return seriesList
     */
    public List<String> getSeriesIds() {
        return seriesIds;
    }

    /**
     * Set a series list
     *
     * @param seriesIds seriesList
     * @return this
     */
    public void setSeriesIds(List<String> seriesIds) {
        this.seriesIds = seriesIds;
    }

    /**
     * Calculate the total volume of routine
     *
     * @return totalVolumen
     */
    @Exclude
    public Task<Integer> getTotalVolume() {
        return DatabaseUtil.getMappedSumWhereIn(Constants.COLLECTION_SERIES, seriesIds,
                Serie::getVolume, Serie.class);

    }

    /**
     * Returns author
     *
     * @return author
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * Set routine author
     *
     * @param authorId author
     * @return this
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Exclude
    public Task<List<Serie>> getSeries() {
        return Serie.getWhereIdIn(seriesIds);
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }

    /**
     * Saves this object on the database
     *
     * @return A task with the inserted object id.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_ROUTINES, this, Routine.class);
    }

    /**
     * Updates this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_ROUTINES, id, this, Routine.class);
    }

    /**
     * Gets a Routine by id.
     *
     * @param id The id of the Routine.
     * @return A task with the Routine as result.
     */
    @Exclude
    public static Task<Routine> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_ROUTINES, id, Routine.class);
    }

    /**
     * Gets a list of Routine by ids.
     *
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Routine>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_ROUTINES, ids, Routine.class);
    }

    /**
     * List all routines made by author.
     *
     * @param authorId The user id.
     * @return A list of routines
     */
    @Exclude
    public static Task<List<Routine>> listByAuthor(String authorId) {
        return DatabaseUtil.getWhereValueIs(Constants.COLLECTION_ROUTINES, "authorId", authorId, Routine.class);
    }

    /**
     * Get all the routines.
     *
     * @return all the routines
     */
    @Exclude
    public static Task<List<Routine>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_ROUTINES, Routine.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return Objects.equals(id, routine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
