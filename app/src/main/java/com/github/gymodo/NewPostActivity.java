package com.github.gymodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gymodo.social.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity {

    private EditText newPostContent;
    private Button newPostPublishBtn;

    private String description;
    private String author;
    private Date date;
    private boolean canPublish = true;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        newPostContent = findViewById(R.id.newPostContent);
        newPostPublishBtn = findViewById(R.id.newPostPublishBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        //TODO make sure there is content to publish to prevent an empty post
        newPostPublishBtn.setOnClickListener(v -> {

            if (canPublish){
                date = Calendar.getInstance().getTime();
                author = firebaseAuth.getCurrentUser().getUid();

                description = newPostContent.getText().toString();

                Post post = new Post();
                post.setAuthorId(author);
                post.setDescription(description);
                post.setCreatedAt(date);
                post.save().addOnSuccessListener(s -> Toast.makeText(NewPostActivity.this, "Post published", Toast.LENGTH_SHORT).show());
            }
        });

    }
}