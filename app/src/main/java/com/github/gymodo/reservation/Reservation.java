package com.github.gymodo.reservation;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.exercise.Exercise;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a reservation in a specified span of time.
 * With a list of users who requested this specific span of time.
 */
public class Reservation {
    @DocumentId
    private String id;
    /** The day and hour this reservation span starts.
     *
     */
    private Date date;
    /**
     * The duration of this reservation time. In hours.
     */
    private int duration;
    /**
     * The list of users ids who reserved this time.
     */
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
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Set users list
     *
     * @param users userList
     * @return this
     */
    public Reservation setUserIds(List<String> users) {
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
    @Exclude
    public Task<List<User>> getUsers() {
        return User.getWhereIdIn(userIds);
    }

    /**
     * Saves this object on the database
     * @return A task with the inserted object id.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_RESERVATIONS, this, Reservation.class);
    }

    /**
     * Updates this object on the database
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_RESERVATIONS, id, this, Reservation.class);
    }

    /**
     * Gets a Exercise by id.
     *
     * @param id The id of the Exercise.
     * @return A task with the Exercise as result.
     */
    @Exclude
    public static Task<Reservation> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_RESERVATIONS, id, Reservation.class);
    }

    /**
     * Gets a list of Reservation by ids.
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Reservation>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_RESERVATIONS, ids, Reservation.class);
    }

    /**
     * Get all the Reservation.
     *
     * @return all the Reservation
     */
    @Exclude
    public static Task<List<Reservation>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_RESERVATIONS, Reservation.class);
    }

    /**
     * Get all the Reservation.
     *
     * @return all the Reservation
     */
    @Exclude
    public static Task<List<Reservation>> listPastDate(Date date) {
        return DatabaseUtil.getWhereValueIsGreatherOrEqual(Constants.COLLECTION_RESERVATIONS, "date", date, Reservation.class);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", userIds=" + userIds +
                '}';
    }
}
