    package com.sp.mini_assignment;

    import android.os.Bundle;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ListView;
    import androidx.fragment.app.Fragment;
    import java.util.ArrayList;

    public class ForumFragment extends Fragment {

        private EditText commentEditText;
        private ListView commentsListView;
        private ArrayList<Comment> commentsList;
        private CommentAdapter commentsAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_forum, container, false);

            // Initialize views and data
            commentEditText = rootView.findViewById(R.id.comment);
            commentsListView = rootView.findViewById(R.id.commentList);

            // Initialize commentsList with two default comments
            commentsList = new ArrayList<>();
            commentsList.add(new Comment("User1532", "I have used this app for about 1 month now, and I have to say that it's incredible to use for the constantly packed carpark at Junction 8!", R.drawable.person1));
            commentsList.add(new Comment("User456", "Just started using this app, and it's a total game-changer! The Junction 8 carpark is always packed, but this app makes parking a breeze. Highly recommend!", R.drawable.person2));

            // Use the custom adapter
            commentsAdapter = new CommentAdapter(
                    requireContext(),
                    R.layout.forum_comment, // Use the custom layout
                    commentsList
            );

            // Set the adapter for the ListView
            commentsListView.setAdapter(commentsAdapter);

            // Set OnKeyListener for the EditText to detect Enter key press
            commentEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        addComment();
                        return true;
                    }
                    return false;
                }
            });

            return rootView;
        }

        private void addComment() {
            // Get the comment from the EditText
            String commentText = commentEditText.getText().toString().trim();

            // Add the comment to the list
            commentsList.add(new Comment("Me", commentText, R.drawable.me));

            // Notify the adapter that the data set has changed
            commentsAdapter.notifyDataSetChanged();

            // Clear the EditText after adding the comment
            commentEditText.setText("");
        }
    }
