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

    private FirebaseFirestore db;
    private String postId = null;
    List<Comment> commentList = new ArrayList<>();
    private Post post;

    private EditText commentInput;
    private Button commentBtn;
    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerViewComments;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();

        readIntent();
        db = FirebaseFirestore.getInstance();
        db.collection(Constants.COLLECTION_POSTS).document(postId).
                addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(CommentsActivity.this, "Error loading comments", Toast.LENGTH_SHORT).show();
                            Log.d("comments", error.toString());
                        }

                        if (value.exists()){
                            post = value.toObject(Post.class);

                            if (post.getCommentIds() != null){
                                post.getComments().addOnSuccessListener(new OnSuccessListener<List<Comment>>() {
                                    @Override
                                    public void onSuccess(List<Comment> comments) {
                                        commentList = comments;
                                        Collections.sort(commentList, new Comparator<Comment>() {
                                            public int compare(Comment o1, Comment o2) {
                                                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                                            }
                                        });
                                        showComments();
                                        /*
                                        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                        commentsAdapter = new CommentsAdapter(getApplicationContext(), commentList);

                                        recyclerViewComments.setAdapter(commentsAdapter);*/
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private void showComments() {
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        commentsAdapter = new CommentsAdapter(getApplicationContext(), commentList);

        recyclerViewComments.setAdapter(commentsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //hook
        commentInput = findViewById(R.id.commentsNewCommentInput);
        commentBtn = findViewById(R.id.commentsNewCommentBtn);
        recyclerViewComments = findViewById(R.id.recyclerViewComments);


        firebaseAuth = FirebaseAuth.getInstance();

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!commentInput.getText().toString().isEmpty()){

                    Date date = Calendar.getInstance().getTime();
                    String authorId = firebaseAuth.getCurrentUser().getUid();

                    Comment comment = new Comment(commentInput.getText().toString(), authorId, date);
                    comment.save().addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {

                            List<String> commentIds = post.getCommentIds();

                            if (commentIds != null){
                                post.getCommentIds().add(s);

                            } else {
                                commentIds = new ArrayList<>();
                                commentIds.add(s);
                                post.setCommentIds(commentIds);

                            }

                            post.update().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    commentInput.setText("");
                                }
                            });
                        }
                    });

                }
            }
        });

    }


    private void readIntent(){
        Intent intent = getIntent();
        if (intent.hasExtra("postID")){
            postId = intent.getStringExtra("postID");
        }
    }
}