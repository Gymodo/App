package com.github.gymodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gymodo.adapters.CommentsAdapter;
import com.github.gymodo.adapters.PostsAdapter;
import com.github.gymodo.reservation.Reservation;
import com.github.gymodo.social.Comment;
import com.github.gymodo.social.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    private String postId = null;
    List<Comment> commentList = new ArrayList<>();
    private Post post;
    private EditText commentInput;
    private CommentsAdapter commentsAdapter;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();

        readIntent();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.COLLECTION_POSTS).document(postId).
                addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(CommentsActivity.this, "Error loading comments", Toast.LENGTH_SHORT).show();
                        Log.d("comments", error.toString());
                    }

                    if (value.exists()) {
                        post = value.toObject(Post.class);

                        if (post.getCommentIds() != null && !post.getCommentIds().isEmpty()) {
                            post.getComments().addOnSuccessListener(comments -> {
                                commentList.clear();
                                commentList.addAll(comments);
                                commentList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
                                commentsAdapter.notifyDataSetChanged();
                            });
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //hook
        commentInput = findViewById(R.id.commentsNewCommentInput);
        Button commentBtn = findViewById(R.id.commentsNewCommentBtn);
        RecyclerView recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentsAdapter = new CommentsAdapter(commentList);
        recyclerViewComments.setAdapter(commentsAdapter);
        
        firebaseAuth = FirebaseAuth.getInstance();

        commentBtn.setOnClickListener(v -> {
            String input = commentInput.getText().toString();
            if (!input.isEmpty()) {

                Date date = Calendar.getInstance().getTime();
                String authorId = firebaseAuth.getCurrentUser().getUid();

                Comment comment = new Comment(input, authorId, date);
                comment.save().addOnSuccessListener(commentId -> {
                    List<String> commentIds = post.getCommentIds();

                    if (commentIds != null) {
                        post.getCommentIds().add(commentId);

                    } else {
                        commentIds = new ArrayList<>();
                        commentIds.add(commentId);
                        post.setCommentIds(commentIds);
                    }
                    
                    post.update().addOnSuccessListener(aVoid -> commentInput.setText(""));
                });
            }
        });
    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("postID")) {
            postId = intent.getStringExtra("postID");
        }
    }
}