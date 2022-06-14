package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;

public class InstagramApplication extends Application {

    public void onCreate() {

        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url)).build());

    }
}
