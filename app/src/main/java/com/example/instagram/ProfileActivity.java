package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";

    TextView tvUsername;
    ImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUsername = findViewById(R.id.tvUsername);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        Intent intent = getIntent();
        ParseUser user = intent.getParcelableExtra("user");

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
}