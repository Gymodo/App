package com.github.gymodo.reservation;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a reservation
 */
public class Reservation {
    @DocumentId
    private String id;
    private Date date;
    private int duration;
    private List<String> userIds;

    /**
     * Build nameless reservation
     */
    public Reservation() {
        userIds = new ArrayList<>();
    }

    /**
     * Build an reservation with the data received by parameter
     *
     * @param date     date
     * @param duration Duration
     * @param userIds  users ids
     */
    public Reservation(Date date, int duration, List<String> userIds) {
        this.date = date;
        this.duration = duration;
        this.userIds = userIds;
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
    public List<String> getUsersIds() {
        return userIds;
    }

    /**
     * Set users list
     *
     * @param users userList
     * @return this
     */
    public Reservation setUsersIds(List<String> users) {
        this.userIds = users;
        return this;
    }

    public String getId() {
        return id;
    }

    public Reservation setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Add user to users list
     *
     * @param userId The user id.
     */
    public void addUserId(String userId) {
        userIds.add(userId);
    }

    /**
     * Gets the users that made this reservation
     * @return A task with a list of users.
     */
    public Task<List<User>> getUsers() {
        return User.getWhereIdIn(userIds);
    }

    /**
     * Saves this object on the database
     * @return A empty task.
     */
    public Task<Void> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_RESERVATIONS, this, Reservation.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_RESERVATIONS, id, this, Reservation.class);
    }

    /**
     * Gets a Exercise by id.
     *
     * @param id The id of the Exercise.
     * @return A task with the Exercise as result.
     */
    public static Task<Reservation> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_RESERVATIONS, id, Reservation.class);
    }

    /**
     * Gets a list of Reservation by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    public static Task<List<Reservation>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_RESERVATIONS, ids, Reservation.class);
    }
}
