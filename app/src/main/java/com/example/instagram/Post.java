package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String USER = "user";

    public Post() {}

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public ParseFile getImage() {
        return getParseFile(IMAGE);
    }

    public ParseUser getUser() {
        return getParseUser(USER);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public void setImage(File image) {
        put(IMAGE, image);
    }

    public void setUser(ParseUser user) {
        put(USER, user);
    }
}
