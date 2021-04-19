package com.github.gymodo.Exercise;


/**
 * Represents a muscle
 *
 * @see Exercise
 * @see Serie
 * @see Routine
 */
public class Muscle {

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
}
