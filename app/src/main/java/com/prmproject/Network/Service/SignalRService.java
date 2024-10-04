package com.prmproject.Network.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.microsoft.signalr.HttpRequestException;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.TransportEnum;
import com.prmproject.clothesstoremobileandroid.BuildConfig;
import com.prmproject.clothesstoremobileandroid.Data.model.ChatMessage;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.Callback;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ChatMessageResponse;

import io.reactivex.rxjava3.core.Single;

public class SignalRService {
    private static String token;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static HubConnection hubConnection;
    private static final int MAX_RETRIES = 3;
    private static Context _context;

    public SignalRService() {
    }

    public void startConnection() {
        hubConnection = HubConnectionBuilder.create(BASE_URL + "hubClothesStore/ChatMessage")
                .withAccessTokenProvider(Single.defer(() -> {
                    return Single.just(token);
                }))
                .withTransport(TransportEnum.WEBSOCKETS)
                .withHandshakeResponseTimeout(30 * 1000)
                .build();

        hubConnection.onClosed(exception -> {
            if (exception != null) {
                Log.e("SignalR", "Connection closed with error: ", exception);
                handleConnectionClosed(exception);
            }
        });

        connect();
    }

    private void handleConnectionClosed(Throwable exception) {
        if (exception instanceof HttpRequestException) {
            HttpRequestException httpException = (HttpRequestException) exception;
            if (httpException.getStatusCode() == 401) {
                Log.e("SignalR", "Unauthorized access. Trying to reconnect...");
                updateToken(null);
                reconnect();
                if(_context != null){
                    SharedPreferences sharedPreferences = _context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("TOKEN_KEY");
                    editor.apply();
                }
            }
        }
    }

    public void reconnect() {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                Thread.sleep(2000);
                connect();
                return;
            } catch (InterruptedException e) {
                Log.e("SignalR", "Reconnect attempt interrupted", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                Log.e("SignalR", "Reconnect attempt failed", e);
            }
        }
        Log.e("SignalR", "Failed to reconnect after " + MAX_RETRIES + " attempts.");
    }

    public void sendMessageToUser(ChatMessage message, Callback<ChatMessageResponse<ChatMessage>> callback) {
        if (hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
            connect();
        }
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.invoke(ChatMessageResponse.class, "SendMessageToUser", message)
                    .subscribe((ChatMessageResponse messageSent) -> {
                        callback.onSuccess(messageSent);
                    }, throwable -> {
                        Log.e("SignalR", "Error sending message: " + throwable.getMessage());
                        callback.onError(throwable);
                    });
        } else {
            Log.e("SignalR", "Hub connection is not connected");
            callback.onError(new Exception("Hub connection is not connected"));
        }
    }

    public void stopSignalRConnection() {
        if (hubConnection != null) {
            hubConnection.stop();
        }
    }

    public void updateToken(String newToken) {
        token = newToken;
    }

    public void connect() {
        hubConnection.start().subscribe(() -> {
            Log.d("SignalR", "Connected to Hub");
        }, error -> {
            Log.e("SignalR", "Error connecting to Hub", error);
            handleConnectionError(error);
        });
    }

    private void handleConnectionError(Throwable error) {
        if (error instanceof HttpRequestException) {
            HttpRequestException httpException = (HttpRequestException) error;
            if (httpException.getStatusCode() == 401) {
                Log.e("SignalR", "Unauthorized access during connect.");
                SharedPreferences sharedPreferences = _context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("TOKEN_KEY");
                editor.apply();
            }
        }
    }

    public void updateContext(Context context){
        _context = context;
    }

    public HubConnection getHubConnection() {
        if (hubConnection == null) {
            startConnection();
        }
        return hubConnection;
    }
}