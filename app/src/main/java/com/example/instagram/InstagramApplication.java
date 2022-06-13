package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;

public class InstagramApplication extends Application {

    public void onCreate() {

        super.onCreate();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jE6WJ8n9Q1HVsUWB3SOSCIYXHlXV3Dh7OFr4nwTM")
                .clientKey("urhlLBA4jIpwk4ffBSO9a5893OHxYB2G3gmFtEBR")
                .server("https://parseapi.back4app.com").build());

    }
}
