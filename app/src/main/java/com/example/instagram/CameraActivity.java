package com.example.instagram;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    public static final String TAG = "CameraActivity";

//    private Button btnSubmitPicture;
//    private EditText etDescription;
//    private Button btnTakePicture;
//    private File photoFile;
//    private ImageView ivPreview;
    private MenuItem progressBar;

//    ActivityResultLauncher<Uri> resultLauncher = registerForActivityResult(
//            new ActivityResultContracts.TakePicture(),
//            new ActivityResultCallback<Boolean>() {
//                @Override
//                public void onActivityResult(Boolean result) {
//                    if (result == true) {
//                        Log.i(TAG, "took picture");
//                        //decoding image
//                        Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//
//                        //setting the image to the image preview in layout
//                        ivPreview = (ImageView) findViewById(R.id.ivPreview);
//                        ivPreview.setImageBitmap(takenImage);
//                    } else {
//                        Log.e(TAG, "picture wasn't taken");
//                    }
//                }
//            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // Display icon in the toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setLogo(R.drawable.nav_logo_whiteout);
        }

//        btnSubmitPicture = findViewById(R.id.btnSubmitPicture);
//        etDescription = findViewById(R.id.etDescription);
//        btnTakePicture = findViewById(R.id.btnTakePicture);
//
//        btnSubmitPicture.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showProgressBar();
//                String description = etDescription.getText().toString();
//                if (description.isEmpty()) {
//                    Log.i(TAG, "Description cannot be empty");
//                } else {
//                    ParseUser user = ParseUser.getCurrentUser();
//                    savePost(description, user);
//                }
//            }
//        });
//
//        btnTakePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // getting a file reference
//                photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//
//                // wrapping File object into a content provider
//                Uri fileProvider = FileProvider.getUriForFile(CameraActivity.this, "com.example.instagram.fileprovider", photoFile);
//
//                // launch intent to open camera
//                resultLauncher.launch(fileProvider);
//                // if (intent.resolveActivity(packageManager) != null)
//                // before we had to check if the intent could be handled and only launch it if it could, do we still have to check with the result launcher?
//
//            }
//        });
    }

//    public void savePost(String description, ParseUser user) {
//        Post post = new Post();
//        post.setDescription(description);
//        post.setUser(user);
//        post.setImage(new ParseFile(photoFile));
//        post.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                    etDescription.setText("");
//                    hideProgressBar();
//                    Toast.makeText(CameraActivity.this, "Post created!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CameraActivity.this, FeedActivity.class));
//                } else {
//                    Log.e(TAG, "Unable to save post");
//                }
//            }
//        });
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        progressBar = menu.findItem(R.id.progressBar);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.miHome) {
//            startActivity(new Intent(this, FeedActivity.class));
//        } else if (item.getItemId() == R.id.logoutBtn) {
//            ParseUser.logOut();
//            Log.i("Logout", String.valueOf(ParseUser.getCurrentUser()));
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//        return false;
//    }

//    public void showProgressBar() {
//        progressBar.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        progressBar.setVisible(false);
//    }
}