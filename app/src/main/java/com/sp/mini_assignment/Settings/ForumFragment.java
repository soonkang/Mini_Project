    package com.sp.mini_assignment.Settings;

    import static android.app.Activity.RESULT_OK;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ListView;
    import androidx.fragment.app.Fragment;

    import com.sp.mini_assignment.Adapters.Comment;
    import com.sp.mini_assignment.Adapters.CommentAdapter;
    import com.sp.mini_assignment.R;

    import java.util.ArrayList;

    import javax.annotation.Nullable;

    public class ForumFragment extends Fragment {

        private final int GALLERY_REQ_CODE = 100;

        private EditText commentEditText;
        private ListView commentsListView;
        private ArrayList<Comment> commentsList;
        private CommentAdapter commentsAdapter;

        ImageView addPhoto;

        Uri selectedImageUri;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_forum, container, false);

            // Initialize views and data
            commentEditText = rootView.findViewById(R.id.comment);
            commentsListView = rootView.findViewById(R.id.commentList);
            addPhoto = rootView.findViewById(R.id.cameraForum);

            // Initialize commentsList with two default comments
            commentsList = new ArrayList<>();
            commentsList.add(new Comment("User1532", "I have used this app for about 1 month now, and I have to say that it's incredible to use for the constantly packed carpark at Junction 8!", null,R.drawable.person1));
            commentsList.add(new Comment("User456", "Just started using this app, and it's a total game-changer! The Junction 8 carpark is always packed, but this app makes parking a breeze. Highly recommend!", null, R.drawable.person2));

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

            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, GALLERY_REQ_CODE);

                }
            });


            return rootView;
        }

        private void addComment() {
            // Get the comment from the EditText
            String commentText = commentEditText.getText().toString().trim();


            // Add the comment to the list
            commentsList.add(new Comment("Me", commentText, selectedImageUri, R.drawable.me));

            // Notify the adapter that the data set has changed
            commentsAdapter.notifyDataSetChanged();

            // Clear the EditText after adding the comment
            commentEditText.setText("");
            addPhoto.setImageResource(R.drawable.camera);
            selectedImageUri = null;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode==RESULT_OK) {
                if(requestCode==GALLERY_REQ_CODE){

                    addPhoto.setImageURI(data.getData());
                    selectedImageUri = data.getData();
                }
            }
        }
    }
