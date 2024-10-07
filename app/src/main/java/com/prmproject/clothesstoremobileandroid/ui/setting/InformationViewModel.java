package com.prmproject.clothesstoremobileandroid.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.prmproject.clothesstoremobileandroid.Data.model.Customer;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.UserRepository;

public class InformationViewModel extends ViewModel {
    private UserRepository userRepository;

    public InformationViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<ObjectResponse> getInfo() {
        return userRepository.getInfo();
    }
    public LiveData<ObjectResponse> updateCustomerInfo(Customer customer) {
        return userRepository.updateCustomer(customer);
    }
}
