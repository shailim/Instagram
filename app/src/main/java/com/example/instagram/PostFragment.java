package com.example.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    public static final String TAG = "PostFragment";

    private Button btnSubmitPicture;
    private EditText etDescription;
    private Button btnTakePicture;
    private File photoFile;
    private ImageView ivPreview;
    private MenuItem progressBar;

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
                        //ivPreview = (ImageView) findViewById(R.id.ivPreview);
                        ivPreview.setImageBitmap(takenImage);
                    } else {
                        Log.e(TAG, "picture wasn't taken");
                    }
                }
            });

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmitPicture = view.findViewById(R.id.btnSubmitPicture);
        etDescription = view.findViewById(R.id.etDescription);
        btnTakePicture = view.findViewById(R.id.btnTakePicture);
        ivPreview = (ImageView) view.findViewById(R.id.ivPreview);

        btnSubmitPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //showProgressBar();
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
                photoFile = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");

                // wrapping File object into a content provider
                Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.example.instagram.fileprovider", photoFile);

                // launch intent to open camera
                resultLauncher.launch(fileProvider);
                // if (intent.resolveActivity(packageManager) != null)
                // before we had to check if the intent could be handled and only launch it if it could, do we still have to check with the result launcher?

            }
        });
    }

    public void savePost(String description, ParseUser user) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    etDescription.setText("");
                    //hideProgressBar();
                    Toast.makeText(getContext(), "Post created!", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getContext(), FeedActivity.class));
                } else {
                    Log.e(TAG, "Unable to save post");
                }
            }
        });
    }

    public void showProgressBar() {
        progressBar.setVisible(true);
    }

    public void hideProgressBar() {
        progressBar.setVisible(false);
    }
}