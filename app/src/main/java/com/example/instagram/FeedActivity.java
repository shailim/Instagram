package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    public static final String TAG = "FeedActivity";

    RecyclerView rvFeed;
    List<Post> posts = new ArrayList<>();
    FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // attaching the adapter and specifying layout for recycler view
        rvFeed = findViewById(R.id.rvFeed);
        adapter = new FeedAdapter(FeedActivity.this, posts);
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(FeedActivity.this));

        //get posts from parse
        queryPosts();
    }

    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> parsePosts, ParseException e) {
                if (parsePosts != null) {
                    posts.addAll(parsePosts);
                    //post list changed so let adapter know so it can refresh views
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Couldn't query posts");
                }
            }
        });
    }
}