package com.prmproject.clothesstoremobileandroid;

import android.app.Application;
import android.content.SharedPreferences;

import com.prmproject.Network.RetrofitClient;

public class ClothesStore extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN_KEY", null);

        if (token != null) {
            RetrofitClient.updateToken(token);
        }
    }
}