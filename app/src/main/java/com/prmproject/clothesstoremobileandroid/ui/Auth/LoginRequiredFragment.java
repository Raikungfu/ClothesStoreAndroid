package com.prmproject.clothesstoremobileandroid.ui.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.prmproject.clothesstoremobileandroid.R;

public class LoginRequiredFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_required, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        Button loginButton = view.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_login_required_to_navigation_login);
        });

        Button registerButton = view.findViewById(R.id.btn_register);
        registerButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_login_required_to_navigation_register);
        });

        return view;
    }
}
