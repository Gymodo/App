package com.github.gymodo.reservation;

import com.github.gymodo.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a reservation
 */
public class Reservation {

    private Date date;
    private int duration;
    private List<User> users;

    /**
     * Build nameless reservation
     */
    public Reservation() {
        users = new ArrayList<>();
    }

    /**
     * Build an reservation with the data received by parameter
     *
     * @param date     date
     * @param duration Duration
     * @param users    usersList
     */
    public Reservation(Date date, int duration, List<User> users) {
        this.date = date;
        this.duration = duration;
        this.users = users;
    }

    /**
     * Returns reservation date
     *
     * @return reservation date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date of reservation
     *
     * @param date reservation date
     * @return this
     */
    public Reservation setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Returns reservation duration
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set reservation duration
     *
     * @param duration duration
     * @return this
     */
    public Reservation setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Returns a list of users
     *
     * @return userList
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Set users list
     *
     * @param users userList
     * @return this
     */
    public Reservation setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    /**
     * Add user to users list
     *
     * @param user User
     */
    public void addUser(User user) {
        users.add(user);
    }
}
