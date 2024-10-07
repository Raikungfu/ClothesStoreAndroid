//package com.prmproject.clothesstoremobileandroid.ui.profile;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.lifecycle.ViewModelProvider;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//
//import com.auth0.android.jwt.JWT;
//import com.prmproject.clothesstoremobileandroid.MainActivity;
//import com.prmproject.clothesstoremobileandroid.R;
//import com.prmproject.Network.RetrofitClient;
//
//public class ProfileFragment extends Fragment {
//
//    private NavController navController;
//    private String userId;
//    private String username;
//
//    private TextView usernameTextView;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//
//        if (!isUserLoggedIn()) {
//            requireLogin();
//            return view;
//        }
//
//        usernameTextView = view.findViewById(R.id.tv_username);
//        decodeToken();
//        if (username != null) {
//            usernameTextView.setText(username);
//        }
//
//        ImageButton linkToOrder = view.findViewById(R.id.order_Btn);
//        linkToOrder.setOnClickListener(v -> {
//            if (userId != null) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("userId", Integer.parseInt(userId));
//                navController.navigate(R.id.navigation_myorder, bundle);
//            }
//        });
//
//        ImageButton linkToSetting = view.findViewById(R.id.settings_Btn);
//        linkToSetting.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("USER_ID", userId);
//            bundle.putString("USERNAME", username);
//            navController.navigate(R.id.navigation_setting, bundle);
//        });
//
//        ImageButton linkToAddress = view.findViewById(R.id.address_Btn);
//        linkToAddress.setOnClickListener(v -> navController.navigate(R.id.navigation_my_address));
//
//        ImageView linkToInformation = view.findViewById(R.id.information_view);
//        linkToInformation.setOnClickListener(v -> navController.navigate(R.id.navigation_information));
//
//        Button logoutBtn = view.findViewById(R.id.logout_Btn);
//        logoutBtn.setOnClickListener(v -> logout());
//
//        return view;
//    }
//
//    private boolean isUserLoggedIn() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("TOKEN_KEY", null);
//        return token != null && !token.isEmpty();
//    }
//
//    private void requireLogin() {
//        navController.popBackStack(R.id.navigation_home, true);
//        navController.navigate(R.id.navigation_login_required);
//        ((MainActivity) requireActivity()).onMessage("Please log in to access your profile.");
//    }
//
//    private void decodeToken() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("TOKEN_KEY", null);
//
//        if (token != null && !token.isEmpty()) {
//            JWT jwt = new JWT(token);
//            userId = jwt.getClaim("Id").asString();
//   //         username = jwt.getClaim("username").asString(); // Assuming username is present in the token
//            RetrofitClient.updateToken(token);
//        }
//    }
//
//    private void logout() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//
//        navController.navigate(R.id.navigation_login_required);
//        ((MainActivity) requireActivity()).onMessage("You have been logged out.");
//    }
//}
package com.prmproject.clothesstoremobileandroid.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private NavController navController;
    private String userId;
    private String username;

    private TextView usernameTextView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        usernameTextView = view.findViewById(R.id.tv_username);

        if (!isUserLoggedIn()) {
            requireLogin();
            return view;
        }

        if (username != null) {
            usernameTextView.setText(username);
        }

        ImageButton linkToOrder = view.findViewById(R.id.order_Btn);
        linkToOrder.setOnClickListener(v -> {
            if (userId != null) {
                Bundle bundle = new Bundle();
                navController.navigate(R.id.navigation_myorder);
            }
        });

        ImageButton linkToSetting = view.findViewById(R.id.settings_Btn);
        linkToSetting.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", userId);
            bundle.putString("USERNAME", username);
            navController.navigate(R.id.navigation_setting, bundle);
        });

        ImageButton linkToAddress = view.findViewById(R.id.address_Btn);
        linkToAddress.setOnClickListener(v -> navController.navigate(R.id.navigation_my_address));


        ImageView linkToInformation = view.findViewById(R.id.information_view);
        linkToInformation.setOnClickListener(v -> {
            if (userId != null) {
                navController.navigate(R.id.navigation_information);
            }
        });


        Button logoutBtn = view.findViewById(R.id.logout_Btn);
        logoutBtn.setOnClickListener(v -> {
            logout();
        });

        return view;
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN_KEY", null);
        return token != null && !token.isEmpty();
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