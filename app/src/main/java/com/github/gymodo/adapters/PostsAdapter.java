package com.github.gymodo.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.CommentsActivity;
import com.github.gymodo.Constants;
import com.github.gymodo.R;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.fragments.WorkoutDetailFragment;
import com.github.gymodo.reservation.Reservation;
import com.github.gymodo.social.Comment;
import com.github.gymodo.social.Post;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    public PostsAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public PostsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.post_row, parent, false);

        return new PostsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.MyViewHolder holder, int position) {

        Post post = mPosts.get(position);
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();


        db.collection(Constants.COLLECTION_POSTS).document(post.getId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            Log.d("errorFirestore", error.toString());
                        }

                        try {

                            if (value.exists()) {

                                Post p = value.toObject(Post.class);
                                if (p.getDescription() != null) {

                                    holder.postContent.setText(p.getDescription());
                                }

                                if (p.getCommentIds() != null && p.getCommentIds().size() > 0) {
                                    holder.commentNum.setText(String.valueOf(p.getCommentIds().size()));
                                }

                                if (p.getLikedByIds() != null) {

                                    if (p.getLikedByIds().size() > 0) {
                                        holder.likeNum.setText(String.valueOf(p.getLikedByIds().size()));
                                    } else {
                                        holder.likeNum.setText("");
                                    }
                                }


                                if (p.getLikedByIds().contains(userID)) {
                                    holder.postRowLike.setImageResource(R.drawable.ic_like_icon);
                                }
                            } else {
                            }

                        } catch (Exception e) {
                            Log.d("error", "Exception: " + e);
                        }
                    }
                });

        //If there is no likes
        if (post.getLikedByIds() == null) {
            post.setLikedByIds(new ArrayList<>());
        }

        if (post.getAuthor() != null) {
            post.getAuthor().addOnSuccessListener(new OnSuccessListener<User>() {
                @Override
                public void onSuccess(User user) {
                    holder.userName.setText(user.getName());
                }
            });
        }

        if (post.getDescription() != null) {
            holder.postContent.setText(post.getDescription());
        }

        if (post.getCommentIds() != null && post.getCommentIds().size() > 0) {
            holder.commentNum.setText(String.valueOf(post.getCommentIds().size()));
        }

        if (post.getLikedByIds() != null && post.getLikedByIds().size() > 0) {
            holder.likeNum.setText(String.valueOf(post.getLikedByIds().size()));
        }

        if (post.getImageUrl() != null) {
            FirebaseStorage.getInstance().getReference().child(post.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    holder.image.setVisibility(View.VISIBLE);
                    Picasso.get().load(uri.toString()).into(holder.image);
                }
            });
        }

        if (post.getRoutineId() != null) {
            Routine.getByID(post.getRoutineId()).addOnSuccessListener(new OnSuccessListener<Routine>() {
                @Override
                public void onSuccess(Routine routine) {
                    holder.workout.setText(routine.getName());
                }
            });

            holder.workout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(v);
                    Bundle bundle = new Bundle();
                    bundle.putString(WorkoutDetailFragment.ARG_ROUTINE_ID, post.getRoutineId());
                    navController.navigate(R.id.workoutDetailFragment, bundle);
                }
            });
        } else {
            holder.workout.setVisibility(View.GONE);
        }




        if (post.getLikedByIds().contains(userID)) {
            holder.postRowLike.setImageResource(R.drawable.ic_like_icon);
        }


        holder.postRowLike.setOnClickListener(v -> {
            if (post.getLikedByIds().contains(userID)) {

                post.getLikedByIds().remove(userID);
                post.update().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.postRowLike.setImageResource(R.drawable.ic_like_icon_outline);
                    }
                });

            } else {
                post.getLikedByIds().add(userID);
                post.update().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        holder.postRowLike.setImageResource(R.drawable.ic_like_icon);
                    }
                });
            }
            animateLikeIcon(holder.postRowLike);
        });

        //Can open comments by clicking icon or num of comments
        holder.commentIcon.setOnClickListener(v -> {

            opencomments(mPosts.get(position).getId());
        });

        holder.commentNum.setOnClickListener(v -> {

            opencomments(mPosts.get(position).getId());
        });


    }

    private void opencomments(String postId) {

        Intent intent = new Intent(mContext, CommentsActivity.class);
        intent.putExtra("postID", postId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        mContext.startActivity(intent);
    }

    void animateLikeIcon(ImageView icon) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(icon, "scaleX", 0f, 1.5f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(icon, "scaleY", 0f, 1.5f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(icon, "scaleX", 1.5f, 1f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(icon, "scaleY", 1.5f, 1f);

        objectAnimator.setDuration(100);
        objectAnimator2.setDuration(100);


        objectAnimator3.setDuration(100);
        objectAnimator4.setDuration(100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4);

        animatorSet.start();
    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView postContent;
        TextView likeNum;
        TextView commentNum;
        TextView workout;

        ImageView image;
        ImageView userAvatar;
        ImageView commentIcon;
        ImageView postRowLike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.postRowUserName);
            postContent = itemView.findViewById(R.id.postRowText);
            likeNum = itemView.findViewById(R.id.postRowLikeNum);
            commentNum = itemView.findViewById(R.id.postRowCommentNum);
            image = itemView.findViewById(R.id.postRowImage);
            userAvatar = itemView.findViewById(R.id.postRowUserAvatar);
            commentIcon = itemView.findViewById(R.id.postRowComment);
            postRowLike = itemView.findViewById(R.id.postRowLike);
            workout = itemView.findViewById(R.id.postRowRoutine);

        }
    }
}
