package com.prmproject.clothesstoremobileandroid.ui.profile;

import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.prmproject.clothesstoremobileandroid.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    NavController navController;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        ImageButton linkToOrder = view.findViewById(R.id.order_Btn);
        linkToOrder.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_myorder);
        });
        ImageButton linkToSetting = view.findViewById(R.id.settings_Btn);
        linkToSetting.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_setting);
        });
        ImageButton linkToAddress = view.findViewById(R.id.address_Btn);
        linkToAddress.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_my_address);
        });
        ImageView linkToInformation=view.findViewById(R.id.information_view);
        linkToInformation.setOnClickListener(v->{
            navController.navigate(R.id.navigation_information);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}