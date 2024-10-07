package com.prmproject.clothesstoremobileandroid.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.prmproject.clothesstoremobileandroid.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private CardView cardPassView;
    private Button PasswordChangeButton;
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // username
        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("USERNAME");

            TextView fullNameTextView = view.findViewById(R.id.tv_full_name);
            if (username != null) {
                fullNameTextView.setText(username);
            }
        }

        cardPassView = view.findViewById(R.id.cardPassView);
        PasswordChangeButton = view.findViewById(R.id.passChange_Btn);

        cardPassView.setVisibility(View.GONE);

        PasswordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardPassView.getVisibility() == View.GONE) {
                    cardPassView.setVisibility(View.VISIBLE);
                    cardPassView.animate().translationY(0).setDuration(300);
                } else {
                    cardPassView.animate().translationY(cardPassView.getHeight()).setDuration(300).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            cardPassView.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

}