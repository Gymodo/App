package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.reservation.Reservation;
import com.github.gymodo.social.Post;
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

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.MyViewHolder holder, int position) {

        Post post = mPosts.get(position);
        Toast.makeText(mContext, "ENtra aqui", Toast.LENGTH_SHORT).show();
        /*
        if (post.getAuthor() != null){
            holder.userName = post.getAuthor().;
        }*/

        if (post.getDescription() != null){
            holder.postContent.setText(post.getDescription());
        }

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.postRowUserName);
            postContent = itemView.findViewById(R.id.postRowText);
            likeNum = itemView.findViewById(R.id.postRowLikeNum);
            commentNum = itemView.findViewById(R.id.postRowCommentNum);
            image = itemView.findViewById(R.id.postRowImage);
            userAvatar = itemView.findViewById(R.id.postRowUserAvatar);
        }
    }
}
