package com.prmproject.clothesstoremobileandroid.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.UserRepository;

public class ProfileViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<ObjectResponse> userProfile;

    public ProfileViewModel() {
        userRepository = new UserRepository();
        userProfile = userRepository.getUser();
    }

    public LiveData<ObjectResponse> getUserProfile() {
        return userRepository.getUser();
    }
}