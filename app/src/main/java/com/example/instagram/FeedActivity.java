package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // find the recycler view and swipe container
        rvFeed = findViewById(R.id.rvFeed);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // pass in the context and list of posts to adapter
        adapter = new FeedAdapter(FeedActivity.this, posts);

        // attaching the adapter and specifying layout for recycler view
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(FeedActivity.this));

        //get posts from parse
        queryPosts();

        // setting the refresh listener for whenever user pulls down to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // call query to get posts again
                ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                query.setLimit(20);
                query.addDescendingOrder("createdAt");
                //async query
                query.findInBackground(new FindCallback<Post>() {
                    @Override
                    public void done(List<Post> posts, ParseException e) {
                        if (posts != null) {
                            //if successful, clear posts in adapter and then add the new list of posts
                            adapter.clear();
                            adapter.addAll(posts);
                            // have to notify that it's done refreshing
                            swipeContainer.setRefreshing(false);
                        } else {
                            Log.e(TAG, "Couldn't refresh posts");
                        }
                    }
                });
            }
        });
    }

    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //limit of 20 posts
        query.setLimit(20);
        //getting newest posts first
        query.addDescendingOrder("createdAt");
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