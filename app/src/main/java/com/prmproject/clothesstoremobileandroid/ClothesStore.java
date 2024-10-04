package com.prmproject.clothesstoremobileandroid;

import android.app.Application;
import android.content.SharedPreferences;

import com.prmproject.Network.RetrofitClient;
import com.prmproject.Network.Service.SignalRService;

public class ClothesStore extends Application {
    private SignalRService signalRService;

    @Override
    public void onCreate() {
        super.onCreate();
        signalRService = new SignalRService();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN_KEY", null);

        if (token != null) {
            RetrofitClient.updateToken(token);
            signalRService.updateToken(token);
            signalRService.updateContext(this);
            signalRService.startConnection();
        }
        RetrofitClient.getInstance();
    }

    public SignalRService getSignalRService() {
        return signalRService;
    }
}