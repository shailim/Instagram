package com.example.instagram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private RecyclerView rvFeed;
    private List<Post> posts = new ArrayList<>();
    private FeedAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFeed = view.findViewById(R.id.rvFeed);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        adapter = new FeedAdapter(getContext(), posts);
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts(null);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts(swipeContainer);
            }
        });
    }

    public void queryPosts(SwipeRefreshLayout sw) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        ParseQuery<User> query2 = ParseQuery.getQuery(User.class);
        query2.whereMatchesQuery("userId", query);
        query2.include("userId");
        //limit of 20 posts
        query.setLimit(20);
        //getting newest posts first
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> parsePosts, ParseException e) {
                if (parsePosts != null) {
                    Log.i(TAG, parsePosts.get(0).toString());
                    Log.i(TAG, "got posts");
                    posts.clear();
                    posts.addAll(parsePosts);
                    //post list changed so let adapter know so it can refresh views
                    adapter.notifyDataSetChanged();
                    // if a swipe container was passed in, set refreshing state to false
                    if (sw != null) {
                        sw.setRefreshing(false);
                    }
                } else {
                    Log.e(TAG, "Couldn't query posts");
                }
            }
        });
    }
}