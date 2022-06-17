package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String USER = "user";
    public static final String LIKES = "likes";
    public static final String PROFILE_PIC = "profilePic";
    public static final String USERID = "userId";

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

    public JSONArray getLikes() { return getJSONArray(LIKES); }

    public ParseFile getProfilePic() { return getParseFile(PROFILE_PIC); }

    public String getUserid() { return getString(USERID); }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public void setImage(ParseFile image) { put(IMAGE, image); }

    public void setUser(ParseUser user) {
        put(USER, user);
    }

    public void setLikes(JSONArray list) { put(LIKES, list); }

    public void setProfilePic(ParseFile image) { put(PROFILE_PIC, image); }

    public void setUserid(String id) { put(USERID, id); }
}
