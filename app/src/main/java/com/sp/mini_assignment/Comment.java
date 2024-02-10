package com.sp.mini_assignment;

public class Comment {
    private String username;
    private String commentText;
    private int userImage;

    public Comment(String username, String commentText, int userImage) {
        this.username = username;
        this.commentText = commentText;
        this.userImage = userImage;
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
}
