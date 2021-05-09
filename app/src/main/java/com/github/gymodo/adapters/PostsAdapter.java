package com.github.gymodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.CommentsActivity;
import com.github.gymodo.R;
import com.github.gymodo.reservation.Reservation;
import com.github.gymodo.social.Comment;
import com.github.gymodo.social.Post;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

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

        if (post.getAuthor() != null){
            post.getAuthor().addOnSuccessListener(new OnSuccessListener<User>() {
                @Override
                public void onSuccess(User user) {
                    holder.userName.setText(user.getName());
                }
            });
        }

        if (post.getDescription() != null){
            holder.postContent.setText(post.getDescription());
        }


        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postID", mPosts.get(position).getId());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                mContext.startActivity(intent);
            }
        });

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

        ImageView image;
        ImageView userAvatar;
        ImageView commentIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.postRowUserName);
            postContent = itemView.findViewById(R.id.postRowText);
            likeNum = itemView.findViewById(R.id.postRowLikeNum);
            commentNum = itemView.findViewById(R.id.postRowCommentNum);
            image = itemView.findViewById(R.id.postRowImage);
            userAvatar = itemView.findViewById(R.id.postRowUserAvatar);
            commentIcon = itemView.findViewById(R.id.postRowComment);
        }
    }
}
