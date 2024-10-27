package com.prmproject.clothesstoremobileandroid.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.UserResponse;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private NavController navController;
    private ImageView userAvt;
    private String userId;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), userResponse -> {
            if (userResponse != null && userResponse.isSuccess()) {
                UserResponse user = (UserResponse) userResponse.getItem();
                binding.tvUsername.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());

                String avatarUrl = user.getAvatar();
                if (avatarUrl != null && !avatarUrl.isEmpty()) {
                    Glide.with(this)
                            .load(avatarUrl)
                            .placeholder(R.drawable.logor)
                            .error(R.drawable.logor)
                            .into(binding.userAvt);
                }
                username = user.getName();

            } else if ((!userResponse.isSuccess() && userResponse.getCodeError() == 401)) {
                ((MainActivity) requireActivity()).onMessage("Session expired. Please log in again.");
                requireLogin();
            } else {
                ((MainActivity) requireActivity()).onMessage("Failed to load profile: " + userResponse.getErrorMessage());
            }
        });

        setupNavigation();

        return binding.getRoot();
    }

    private void setupNavigation() {
        ImageView linkToOrder = binding.orderBtn;
        linkToOrder.setOnClickListener(v -> {

                navController.navigate(R.id.navigation_myorder);

        });

        ImageView linkToSetting = binding.settingsBtn;
        linkToSetting.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", username);
            navController.navigate(R.id.navigation_setting, bundle);
        });

        ImageView linkToAddress = binding.addressBtn;
        linkToAddress.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", username);
            navController.navigate(R.id.navigation_my_address, bundle);
        });
        ImageView linkToInformation = binding.informationView;
        linkToInformation.setOnClickListener(v -> {

                navController.navigate(R.id.navigation_information);

        });

        Button logoutBtn = binding.logoutBtn;
        logoutBtn.setOnClickListener(v -> logout());
    }


    private void requireLogin() {
        navController.popBackStack(R.id.navigation_home, true);
        navController.navigate(R.id.navigation_login_required);
        ((MainActivity) requireActivity()).onMessage("Please log in to access your profile.");
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        navController.navigate(R.id.navigation_login_required);
        ((MainActivity) requireActivity()).onMessage("You have been logged out.");
    }
}
