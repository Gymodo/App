package com.github.gymodo.exercise;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Exercise
 *
 * @see Muscle
 * @see Serie
 * @see Routine
 */
public class Exercise {

    private String name;
    private String description;
    private List<Muscle> muscles;

    /**
     * Build nameless exercise
     */
    public Exercise() {
        this.muscles = new ArrayList<>();
    }

    /**
     * Build an exercise with the data received by parameter
     *
     * @param name        Exercise name
     * @param description Exercise description
     * @param muscles     Muscle targets
     */
    public Exercise(String name, String description, List<Muscle> muscles) {
        this.name = name;
        this.description = description;
        this.muscles = muscles;
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
    public List<Muscle> getMuscles() {
        return muscles;
    }

    /**
     * Set muscle list
     *
     * @param muscles muscleList
     * @return this
     */
    public Exercise setMuscles(List<Muscle> muscles) {
        this.muscles = muscles;
        return this;
    }
}
