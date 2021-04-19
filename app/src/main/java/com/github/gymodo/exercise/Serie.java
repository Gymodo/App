package com.github.gymodo.exercise;

/**
 * Represents a Exercise
 *
 * @see Muscle
 * @see Exercise
 * @see Routine
 */
public class Serie {

    private int reps;
    private int weight;
    private Exercise exercise;

    /**
     * Build nameless exercise
     */
    public Serie() {
    }

    /**
     * Build an serie with the data received by parameter
     *
     * @param reps     Repetitions
     * @param weight   Weight
     * @param exercise exercise
     */
    public Serie(int reps, int weight, Exercise exercise) {
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
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
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Set exercise to serie
     *
     * @param exercise exercise
     * @return this
     */
    public Serie setExercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }
}
