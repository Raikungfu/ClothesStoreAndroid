package com.prmproject.clothesstoremobileandroid.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.prmproject.clothesstoremobileandroid.databinding.FragmentDashboardBinding;

public class ShopFragment extends Fragment {

private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}