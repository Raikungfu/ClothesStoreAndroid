//package com.prmproject.clothesstoremobileandroid.ui.setting;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import com.bumptech.glide.Glide;
//import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
//import com.prmproject.clothesstoremobileandroid.R;
//import com.prmproject.clothesstoremobileandroid.databinding.FragmentInformationBinding;
//
//import static android.content.ContentValues.TAG;
//
//public class InformationFragment extends Fragment {
//    private FragmentInformationBinding binding;
//    private InformationViewModel informationViewModel;
//    private EditText txtFullName, txtAddress;
//    private ImageView imgAvt;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentInformationBinding.inflate(inflater, container, false);
//        informationViewModel = new ViewModelProvider(this).get(InformationViewModel.class);
//
//        setupViews();
//        loadCustomerInfoFromArguments();
//
//        return binding.getRoot();
//    }
//
//    private void setupViews() {
//        txtFullName = binding.txtFullname;
//        txtAddress = binding.txtAddress;
//        imgAvt = binding.customerAvatar;
//    }
//
//    private void loadCustomerInfoFromArguments() {
//        Bundle args = getArguments();
//        if (args != null) {
//            int userId = args.getInt("userId", -1);
//            if (userId != -1) {
//                loadCustomerInfoByUserId(userId);
//            }
//        }
//    }
//
//    private void loadCustomerInfoByUserId(int userId) {
//        informationViewModel.getCustomerInfo(userId).observe(getViewLifecycleOwner(), customerResponse -> {
//            if (customerResponse != null && customerResponse.isSuccess()) {
//                Customer customer = (Customer) customerResponse.getItem();
//                displayCustomerInfo(customer);
//            }
//        });
//    }
//
//    private void displayCustomerInfo(Customer customer) {
//            txtFullName.setText(customer.getName());
//            txtAddress.setText(customer.getAddress());
//            if (customer.getAvt() != null && !customer.getAvt().isEmpty()) {
//                Glide.with(this)
//                        .load(customer.getAvt())
//                        .placeholder(R.drawable.logor)
//                        .error(R.drawable.logor)
//                        .into(imgAvt);
//            } else {
//                imgAvt.setImageResource(R.drawable.default_avatar);
//
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
package com.prmproject.clothesstoremobileandroid.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentInformationBinding;

public class InformationFragment extends Fragment {
    private FragmentInformationBinding binding;
    private InformationViewModel informationViewModel;
    private EditText txtFullName, txtAddress;
    private ImageView imgAvt;
    private Button updateInfoBtn;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformationBinding.inflate(inflater, container, false);
        informationViewModel = new ViewModelProvider(this).get(InformationViewModel.class);

        setupViews();
        loadCustomerInfoFromArguments();
        setupUpdateButton();

        return binding.getRoot();
    }

    private void setupViews() {
        txtFullName = binding.txtFullname;
        txtAddress = binding.txtAddress;
        imgAvt = binding.customerAvatar;
        updateInfoBtn = binding.updateInfoBtn;
    }

    private void loadCustomerInfoFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            userId = args.getInt("userId", -1);
            if (userId != -1) {
                loadCustomerInfoByUserId(userId);
            }
        }
    }

    private void loadCustomerInfoByUserId(int userId) {
        informationViewModel.getCustomerInfo(userId).observe(getViewLifecycleOwner(), customerResponse -> {
            if (customerResponse != null && customerResponse.isSuccess()) {
                Customer customer = (Customer) customerResponse.getItem();
                displayCustomerInfo(customer);
            }
        });
    }

    private void displayCustomerInfo(Customer customer) {
        txtFullName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());

        if (customer.getAvt() != null && !customer.getAvt().isEmpty()) {
            Glide.with(this)
                    .load(customer.getAvt())
                    .placeholder(R.drawable.logor)
                    .error(R.drawable.logor)
                    .into(imgAvt);
        } else {
            imgAvt.setImageResource(R.drawable.default_avatar);
        }
    }

    private void setupUpdateButton() {
        updateInfoBtn.setOnClickListener(v -> {
            String name = txtFullName.getText().toString().trim();
            String address = txtAddress.getText().toString().trim();

            if (validateInputs(name, address)) {
                Customer updatedCustomer = new Customer();
                updatedCustomer.setName(name);
                updatedCustomer.setAddress(address);

                updateCustomerInfo(updatedCustomer);
            }
        });
    }

    private boolean validateInputs(String name, String address) {
        if (name.isEmpty() || address.isEmpty() ) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateCustomerInfo(Customer customer) {
        informationViewModel.updateCustomerInfo(userId, customer).observe(getViewLifecycleOwner(), updateResponse -> {
            if (updateResponse != null && updateResponse.isSuccess()) {
                Toast.makeText(getContext(), "Customer info updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update customer info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
