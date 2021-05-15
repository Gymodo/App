package com.github.gymodo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gymodo.R;
import com.github.gymodo.social.Comment;
import com.github.gymodo.user.User;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Adapter for comments.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final List<Comment> commentList;

    public CommentsAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comments_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        commentList.get(position).getAuthor().addOnSuccessListener(user -> holder.commentsRowUserName.setText(user.getName()));
        holder.commentsRowComment.setText(commentList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView commentsRowUserAvatar;
        TextView commentsRowUserName;
        TextView commentsRowComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentsRowUserAvatar = itemView.findViewById(R.id.commentsRowUserAvatar);
            commentsRowUserName = itemView.findViewById(R.id.commentsRowUserName);
            commentsRowComment = itemView.findViewById(R.id.commentsRowComment);
        }
    }
}
