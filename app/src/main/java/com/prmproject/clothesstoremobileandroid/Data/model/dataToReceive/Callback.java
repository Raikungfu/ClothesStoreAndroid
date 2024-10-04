package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public interface Callback<T> {
    void onSuccess(T result);
    void onError(Throwable throwable);
}