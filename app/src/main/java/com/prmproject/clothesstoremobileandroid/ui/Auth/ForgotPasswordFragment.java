package com.prmproject.clothesstoremobileandroid.ui.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;


import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.UserForgotPassword;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;

public class ForgotPasswordFragment extends Fragment {
    private EditText emailEditText, otpEditText, newPasswordEditText, confirmPasswordEditText;
    private Button requestOtpButton, verifyOtpButton, resetPasswordButton, backToEmailButton;
    private AuthViewModel authViewModel;
    private UserForgotPassword user = new UserForgotPassword();
    private String tokenVerifyOtp;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        emailEditText = view.findViewById(R.id.editTextEmail);
        otpEditText = view.findViewById(R.id.editTextOtp);
        newPasswordEditText = view.findViewById(R.id.editTextNewPassword);
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword);
        requestOtpButton = view.findViewById(R.id.buttonRequestOtp);
        verifyOtpButton = view.findViewById(R.id.buttonVerifyOtp);
        resetPasswordButton = view.findViewById(R.id.buttonResetPassword);
        backToEmailButton = view.findViewById(R.id.buttonBackToEmail);

        requestOtpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                requestOtp(email);
            }
        });

        verifyOtpButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString();
            if (otp.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
            } else {
                verifyOtp(Integer.parseInt(otp));
            }
        });

        resetPasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            } else {
                resetPassword(newPassword);
            }
        });

        backToEmailButton.setOnClickListener(v -> {
            otpEditText.setVisibility(View.GONE);
            verifyOtpButton.setVisibility(View.GONE);
            newPasswordEditText.setVisibility(View.GONE);
            confirmPasswordEditText.setVisibility(View.GONE);
            resetPasswordButton.setVisibility(View.GONE);
            backToEmailButton.setVisibility(View.GONE);

            emailEditText.setVisibility(View.VISIBLE);
            requestOtpButton.setVisibility(View.VISIBLE);
        });

        TextView linkToLogin = view.findViewById(R.id.txt_login);
        linkToLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_forgot_password_to_navigation_login);
        });

        TextView linkToSignUp = view.findViewById(R.id.txt_register);
        linkToSignUp.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_forgot_password_to_navigation_register);
        });

        return view;
    }

    private void requestOtp(String email) {
        user.setEmail(email);
        authViewModel.sendOtpToEmail(user).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isStatus()) {
                ((MainActivity) getActivity()).onMessage("OTP sent to email! Please check your email...");
                tokenVerifyOtp = response.getToken();
                otpEditText.setVisibility(View.VISIBLE);
                verifyOtpButton.setVisibility(View.VISIBLE);
                backToEmailButton.setVisibility(View.VISIBLE);
                otpEditText.setText("");

                emailEditText.setVisibility(View.GONE);
                requestOtpButton.setVisibility(View.GONE);
            } else {
                ((MainActivity) getActivity()).onMessage("OTP send failed! " + response.getMessage());
            }
        });
    }

    private void verifyOtp(int otp) {
        user.setOtp(otp);
        authViewModel.verifyOtp(user, tokenVerifyOtp).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isStatus()) {
                tokenVerifyOtp = response.getToken();

                newPasswordEditText.setVisibility(View.VISIBLE);
                confirmPasswordEditText.setVisibility(View.VISIBLE);
                resetPasswordButton.setVisibility(View.VISIBLE);

                newPasswordEditText.setText("");
                confirmPasswordEditText.setText("");

                otpEditText.setVisibility(View.GONE);
                verifyOtpButton.setVisibility(View.GONE);
                ((MainActivity) getActivity()).onMessage("Verify OTP successful! Reset password now..." + response.getMessage());
            } else {
                ((MainActivity) getActivity()).onMessage("Verify OTP failed! " + response.getMessage());
            }
        });
    }

    private void resetPassword(String newPassword) {
        user.setNewPassword(newPassword);
        authViewModel.resetPassword(user, tokenVerifyOtp).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isStatus()) {
                ((MainActivity) getActivity()).onMessage("Reset password successful! Login now..." + response.getMessage());
                navController.navigate(R.id.action_navigation_forgot_password_to_navigation_login);
            } else {
                ((MainActivity) getActivity()).onMessage("Reset password failed! " + response.getMessage());
            }
        });
    }
}