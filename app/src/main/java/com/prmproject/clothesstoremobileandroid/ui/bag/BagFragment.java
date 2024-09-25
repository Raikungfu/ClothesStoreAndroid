package com.prmproject.clothesstoremobileandroid.ui.bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.prmproject.clothesstoremobileandroid.databinding.FragmentBagBinding;

public class BagFragment extends Fragment {

private FragmentBagBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        BagViewModel bagViewModel =
                new ViewModelProvider(this).get(BagViewModel.class);

    binding = FragmentBagBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textBag;
        bagViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}