package com.github.gymodo.exercise;


import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.food.Food;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.List;

/**
 * Represents a muscle
 *
 * @see Exercise
 * @see Serie
 * @see Routine
 */
public class Muscle {
    @DocumentId
    private String id;
    private String name;

    /**
     * Build nameless muscle
     */
    public Muscle() {
    }

    /**
     * Build a muscle with the name received by parameter
     *
     * @param name
     */
    public Muscle(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the muscle
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set muscle list
     *
     * @param name
     * @return this
     */
    public Muscle setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public Muscle setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Saves this object on the database
     * @return A task with the inserted object id.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_MUSCLES, this, Muscle.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_MUSCLES, id, this, Muscle.class);
    }

    /**
     * Gets a Muscle by id.
     *
     * @param id The id of the Muscle.
     * @return A task with the Muscle as result.
     */
    @Exclude
    public static Task<Muscle> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_MUSCLES, id, Muscle.class);
    }

    /**
     * Gets a list of Muscle by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Muscle>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_MUSCLES, ids, Muscle.class);
    }
}
