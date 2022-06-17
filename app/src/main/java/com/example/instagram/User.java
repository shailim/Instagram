package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject{

    public static final String USERNAME = "username";
    public static final String PROFILE_PIC = "profile_pic";
    public static final String PASSWORD = "password";

    public User() {

    }

    public String getUsername() { return getString(USERNAME); }

    public ParseFile getProfilepic() { return getParseFile(PROFILE_PIC); }

    public void setUsername(String name) { put(USERNAME, name); }

    public void setProfilePic(ParseFile file) { put(PROFILE_PIC, file); }

    public void setPassword(String password) { put(PASSWORD, password); }
}
