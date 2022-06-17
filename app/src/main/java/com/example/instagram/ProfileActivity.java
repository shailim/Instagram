package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";

    TextView tvUsername;
    ImageView ivProfilePic;
    RecyclerView gridPosts;
    List<Post> posts = new ArrayList<>();
    GridPostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUsername = findViewById(R.id.tvUsername);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        gridPosts = findViewById(R.id.gridPosts);

        Intent intent = getIntent();
        ParseUser user = intent.getParcelableExtra("user");

        //get all posts for user
        queryPosts(user.getObjectId());

        adapter =  new GridPostsAdapter(this, posts);
        gridPosts.setAdapter(adapter);
        gridPosts.setLayoutManager(new GridLayoutManager(this, 3));

        if (user != null) {
            //get username and profile picture from user object and set it to views
            String username = user.getUsername();
            String url = "";
            ParseFile profilePic = user.getParseFile("profile_pic");
            if (profilePic != null) {
                url = profilePic.getUrl();
            }
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .circleCrop()
                    .into(ivProfilePic);
            tvUsername.setText(username);
        } else {
            Log.e(TAG, "couldn't get user data");
        }

    }

    public void queryPosts(String id) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo("userId", id);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> parsePosts, ParseException e) {
                if (parsePosts != null) {
                    posts.addAll(parsePosts);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Couldn't query posts");
                }
            }
        });
    }
}