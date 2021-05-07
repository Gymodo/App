package com.github.gymodo.social;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;

public class Comment {
    @DocumentId
    private String id;
    private String text;
    private String authorId;
    private Date createdAt;

    public Comment() {
    }

    public Comment(String text, String authorId, Date createdAt) {
        this.text = text;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * Saves this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_COMMENTS, this, Comment.class);
    }

    /**
     * Updates this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_COMMENTS, id, this, Comment.class);
    }

    /**
     * Gets a Post by id.
     *
     * @param id The id of the Comment.
     * @return A task with the Comment as result.
     */
    @Exclude
    public static Task<Comment> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_COMMENTS, id, Comment.class);
    }

    /**
     * Gets a list of users by ids.
     *
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Comment>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_COMMENTS, ids, Comment.class);
    }

    @Exclude
    public Task<Void> delete() {
        return DatabaseUtil.deleteObject(Constants.COLLECTION_COMMENTS, id, Comment.class);
    }

    /**
     * Get all the Comment.
     *
     * @return all the Comment
     */
    @Exclude
    public static Task<List<Comment>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_COMMENTS, Comment.class);
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }
}
