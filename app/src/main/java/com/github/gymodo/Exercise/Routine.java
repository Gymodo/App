package com.github.gymodo.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Routine
 *
 * @see Muscle
 * @see Serie
 * @see Exercise
 */
public class Routine {

    private String name;
    private List<Serie> series;

    /**
     * Build nameless routine
     */
    public Routine() {
        series = new ArrayList<>();
    }

    /**
     * Build an routine with the data received by parameter
     *
     * @param name   name
     * @param series series
     */
    public Routine(String name, List<Serie> series) {
        this.name = name;
        this.series = series;
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
    public List<Serie> getSeries() {
        return series;
    }

    /**
     * Set a series list
     *
     * @param series seriesList
     * @return this
     */
    public Routine setSeries(List<Serie> series) {
        this.series = series;
        return this;
    }

    /**
     * Calculate the total volume of routine
     *
     * @return totalVolumen
     */
    public Float getTotalVolumen() {

        Float totalVolumen = null;

        for (Serie s : series) {
            totalVolumen += (s.getReps() * s.getWeight());
        }

        return totalVolumen * series.size();
    }
}
