package com.sp.mini_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forum_comment, parent, false);
        }

        // Get the comment at the specified position
        Comment comment = getItem(position);

        // Set the comment text in the TextView
        TextView userCommentTextView = convertView.findViewById(R.id.userComment);
        userCommentTextView.setText(comment.getCommentText());

        // Set default user image (You can customize this part based on user information)
        ImageView userImageView = convertView.findViewById(R.id.userImage);
        userImageView.setImageResource(comment.getUserImage()); // Use a default image

        // Set username
        TextView usernameTextView = convertView.findViewById(R.id.usernameName);
        usernameTextView.setText(comment.getUsername());

        return convertView;
    }
}
