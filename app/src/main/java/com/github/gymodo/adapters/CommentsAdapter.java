package com.github.gymodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.social.Comment;
import com.github.gymodo.social.Post;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Comment> mComments;

    public CommentsAdapter(Context mContext, List<Comment> mComments) {
        this.mContext = mContext;
        this.mComments = mComments;
    }

    @NonNull
    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.comments_row, parent, false);

        return new CommentsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.MyViewHolder holder, int position) {

        mComments.get(position).getAuthor().addOnSuccessListener(new OnSuccessListener<User>() {
            @Override
            public void onSuccess(User user) {
                holder.commentsRowUserName.setText(user.getName());
            }
        });
        holder.commentsRowComment.setText(mComments.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView commentsRowUserAvatar;
        TextView commentsRowUserName;
        TextView commentsRowComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            commentsRowUserAvatar = itemView.findViewById(R.id.commentsRowUserAvatar);
            commentsRowUserName = itemView.findViewById(R.id.commentsRowUserName);
            commentsRowComment = itemView.findViewById(R.id.commentsRowComment);

        }
    }
}
