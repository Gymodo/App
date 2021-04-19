package com.github.gymodo.exercise;

import com.github.gymodo.user.User;

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
    private User autor;
    private List<Serie> series;


    /**
     * Build nameless routine
     */
    public Routine() {
        this.series = new ArrayList<>();
    }

    /**
     * Build an routine with the data received by parameter
     *
     * @param name   name
     * @param series series
     */
    public Routine(String name, User autor, List<Serie> series) {
        this.name = name;
        this.autor = autor;
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

    /**
     * Returns author
     *
     * @return author
     */
    public User getAutor() {
        return autor;
    }

    /**
     * Set routine author
     *
     * @param autor author
     * @return this
     */
    public Routine setAutor(User autor) {
        this.autor = autor;
        return this;
    }
}
