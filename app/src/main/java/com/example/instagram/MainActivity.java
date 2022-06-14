package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Button btnLogout;
    private Button btnSubmitPicture;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btnSubmitPicture = findViewById(R.id.btnSubmitPicture);
        etDescription = findViewById(R.id.etDescription);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Log.i("Logout", String.valueOf(ParseUser.getCurrentUser()));
            }
        });

        btnSubmitPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Log.i(TAG, "Description cannot be empty");
                } else {
                    ParseUser user = ParseUser.getCurrentUser();
                    savePost(description, user);
                }
            }
        });

        queryPosts();
    }

    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (posts != null) {
                    for (Post post : posts) {
                        Log.i(TAG, post.getDescription());
                    }
                } else {
                    Log.e(TAG, "Couldn't query posts");
                }
            }
        });
    }

    public void savePost(String description, ParseUser user) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    etDescription.setText("");
                    Log.i(TAG, "post saved!");
                } else {
                    Log.e(TAG, "Unable to save post");
                }
            }
        });
    }
}