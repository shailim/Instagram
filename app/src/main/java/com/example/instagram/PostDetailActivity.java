package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.io.File;
import java.util.Date;

public class PostDetailActivity extends AppCompatActivity {

    ImageView ivPicture;
    TextView tvUsername;
    TextView tvCaption;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ivPicture = findViewById(R.id.ivPicture);
        tvUsername = findViewById(R.id.tvUsername);
        tvCaption = findViewById(R.id.tvCaption);
        tvDate = findViewById(R.id.tvDate);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String caption = intent.getStringExtra("caption");
        String date = intent.getStringExtra("date");
        ParseFile image = intent.getParcelableExtra("image");

        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivPicture);
            ivPicture.setVisibility(View.VISIBLE);
        }
        tvUsername.setText(username);
        tvCaption.setText(caption);
        tvDate.setText(date);

    }
}