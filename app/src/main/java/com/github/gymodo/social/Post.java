package com.github.gymodo.social;

import com.github.gymodo.Constants;
import com.github.gymodo.DatabaseUtil;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    @DocumentId
    private String id;
    private Date createdAt;
    private String authorId;
    private String description;
    /**
     * List of user ids whom liked this post.
     */
    private List<String> likedByIds = new ArrayList<>();
    private List<String> commentIds = new ArrayList<>();
    private String imageUrl;
    // Routine = Workout
    private String routineId;

    public Post() {
    }

    public Post(Date createdAt, String authorId, String description, List<String> likedByIds, List<String> commentIds, String imageUrl, String routineId) {
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.description = description;
        this.likedByIds = likedByIds;
        this.commentIds = commentIds;
        this.imageUrl = imageUrl;
        this.routineId = routineId;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLikedByIds() {
        return likedByIds;
    }

    public void setLikedByIds(List<String> likedByIds) {
        this.likedByIds = likedByIds;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    /**
     * Saves this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<String> save() {
        return DatabaseUtil.saveObject(Constants.COLLECTION_POSTS, this, Post.class);
    }

    /**
     * Updates this object on the database
     *
     * @return A empty task.
     */
    @Exclude
    public Task<Void> update() {
        return DatabaseUtil.updateObject(Constants.COLLECTION_POSTS, id, this, Post.class);
    }

    /**
     * Gets a Post by id.
     *
     * @param id The id of the Post.
     * @return A task with the Post as result.
     */
    @Exclude
    public static Task<Post> getByID(String id) {
        return DatabaseUtil.getByID(Constants.COLLECTION_POSTS, id, Post.class);
    }

    /**
     * Gets a list of Post by ids.
     *
     * @param ids The list of ids.
     * @return A task with a list of ids.
     */
    @Exclude
    public static Task<List<Post>> getWhereIdIn(List<String> ids) {
        return DatabaseUtil.getWhereIdIn(Constants.COLLECTION_POSTS, ids, Post.class);
    }

    @Exclude
    public Task<Void> delete() {
        return DatabaseUtil.deleteObject(Constants.COLLECTION_POSTS, id, Post.class);
    }

    /**
     * Get all the Posts.
     *
     * @return all the Posts
     */
    @Exclude
    public static Task<List<Post>> listAll() {
        return DatabaseUtil.getAll(Constants.COLLECTION_POSTS, Post.class);
    }


    /**
     * Get all the Posts.
     *
     * @return all the Posts
     */
    @Exclude
    public static Task<List<Post>> listAllOrdered() {
        return DatabaseUtil.getAllOrderBydate(Constants.COLLECTION_POSTS, Post.class);
    }

    @Exclude
    public int getTotalLikes() {
        return likedByIds.size();
    }

    /**
     * Get all the Post.
     *
     * @return all the Post
     */

    @Exclude
    public Task<List<Comment>> getComments() {
        return Comment.getWhereIdIn(commentIds);
    }

    @Exclude
    public Task<User> getAuthor() {
        return User.getByID(authorId);
    }
}
