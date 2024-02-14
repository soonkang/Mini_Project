package com.sp.mini_assignment.Adapters;

import android.net.Uri;

public class Comment {
    private String username;
    private String commentText;
    private int userImage;

    Uri imageuri;

    public Comment(String username, String commentText, Uri imageuri , int userImage) {
        this.username = username;
        this.commentText = commentText;
        this.userImage = userImage;
        this.imageuri = imageuri;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getUserImage() {
        return userImage;
    }

    public Uri getImageUri() {return imageuri;}
}
