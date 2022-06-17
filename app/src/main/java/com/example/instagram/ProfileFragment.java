package com.example.instagram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    TextView tvUsername;
    ImageView ivProfilePic;
    RecyclerView gridPosts;
    List<Post> posts = new ArrayList<>();
    GridPostsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        gridPosts = view.findViewById(R.id.gridPosts);

        ParseUser user = ParseUser.getCurrentUser();
        queryPosts(user.getObjectId());

        adapter =  new GridPostsAdapter(getContext(), posts);
        gridPosts.setAdapter(adapter);
        gridPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));

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