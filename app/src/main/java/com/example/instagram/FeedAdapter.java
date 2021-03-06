package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>  {

    private Context context;
    private List<Post> posts;

    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // initially inflating the layout for each post because no views exist yet
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feed_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        //when a view is reused, the data needs to be updated for the current position post
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        ImageView ivPicture;
        TextView tvCaption;
        TextView tvDate;
        ImageButton ibLike;
        ImageButton ibComment;
        ImageView ivProfilePic;
        String username = "";
        String date;
        ParseUser user;
        ParseFile profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //getting the reference to the individual views in the feed_post layout
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvDate = itemView.findViewById(R.id.tvDate);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        }

        public void bind(Post post) {
            //for each post, we set the data
            try {
                // username is only working if i use fetch() ??
                user = post.getUser().fetch();
                username = user.getUsername();
                profilePic = user.getParseFile("profile_pic");
                tvUsername.setText(username);
            } catch (ParseException e) {
                e.printStackTrace();
                tvUsername.setText("Loading...");
            }

            String url = "";
            if (profilePic != null) {
                url = profilePic.getUrl();
            }
            Glide.with(context).load(url)
                    .placeholder(R.drawable.placeholder)
                    .circleCrop()
                    .into(ivProfilePic);

            // should clean up and put all the onClickListeners in another function
            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("user", post.getUser());
                    context.startActivity(intent);
                }
            });

            ivProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("user", post.getUser());
                    context.startActivity(intent);
                }
            });

            tvCaption.setText(username + " " + post.getDescription());

            // formatting date
            date = calculateTimeAgo(post.getCreatedAt());
            tvDate.setText(date);

            ivPicture.setVisibility(View.GONE);
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivPicture);
                ivPicture.setVisibility(View.VISIBLE);

                // clicking on the image opens up the post detail view
                ivPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // pass in the data through intent
                        Intent i = new Intent(context, PostDetailActivity.class);
                        i.putExtra("username", username);
                        i.putExtra("caption", post.getDescription());
                        i.putExtra("image", image);
                        i.putExtra("date", date);
                        context.startActivity(i);
                    }
                });
            }

            // listening if the post is liked
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONArray list = post.getLikes();
                    list.put(ParseUser.getCurrentUser().getObjectId());
                    post.setLikes(list);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                // change heart icon to the active version
                            }
                        }
                    });
                }
            });
        }
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }
}
