package com.github.gymodo.exercise;

import com.google.firebase.firestore.DocumentId;

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
    public Serie(int reps, int weight, String exerciseId) {
        this.reps = reps;
        this.weight = weight;
        this.exerciseId = exerciseId;
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

    public String getId() {
        return id;
    }

    public Serie setId(String id) {
        this.id = id;
        return this;
    }
}
