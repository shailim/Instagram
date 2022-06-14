package com.example.instagram;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Button btnLogout;
    private Button btnSubmitPicture;
    private EditText etDescription;
    private Button btnTakePicture;
    private File photoFile;
    private ImageView ivPreview;

    ActivityResultLauncher<Uri> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result == true) {
                        Log.i(TAG, "took picture");
                        //decoding image
                        Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                        //setting the image to the image preview in layout
                        ivPreview = (ImageView) findViewById(R.id.ivPreview);
                        ivPreview.setImageBitmap(takenImage);
                    } else {
                        Log.e(TAG, "picture wasn't taken");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btnSubmitPicture = findViewById(R.id.btnSubmitPicture);
        etDescription = findViewById(R.id.etDescription);
        btnTakePicture = findViewById(R.id.btnTakePicture);

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

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting a file reference
                photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");

                // wrapping File object into a content provider
                Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.example.instagram.fileprovider", photoFile);

                // launch intent to open camera
                resultLauncher.launch(fileProvider);
                // if (intent.resolveActivity(packageManager) != null)
                // before we had to check if the intent could be handled and only launch it if it could, do we still have to check with the result launcher?

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