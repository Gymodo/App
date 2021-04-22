package com.github.gymodo.exercise;

import com.github.gymodo.Constants;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
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
    public Routine setSeriesIds(List<String> seriesIds) {
        this.seriesIds = seriesIds;
        return this;
    }

    /**
     * Calculate the total volume of routine
     *
     * @return totalVolumen
     */
    public Task<Float> getTotalVolumen() {
        return getSeries()
                .continueWith(series -> {
                    float totalVolumen = 0;

                    for (Serie serie : series.getResult()) {
                        totalVolumen += (serie.getReps() * serie.getWeight());
                    }

                    return totalVolumen;
                });
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
    public Routine setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public Task<List<Serie>> getSeries() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_SERIES)
                .whereIn(FieldPath.documentId(), seriesIds)
                .get()
                .continueWith(x ->
                        x.getResult()
                                .getDocuments()
                                .parallelStream()
                                .map(y -> y.toObject(Serie.class))
                                .collect(Collectors.toList())
                );
    }

    public Task<User> getAuthor() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(Constants.COLLECTION_USERS)
                .document(authorId)
                .get()
                .continueWith(x -> x.getResult().toObject(User.class));
    }
}
