package com.prmproject.clothesstoremobileandroid.ui.payment;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.TokenResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.PaymentRepository;


public class PaymentViewModel extends ViewModel {
    private PaymentRepository paymentRepository;
    private MutableLiveData<String> paymentResult;
    private MutableLiveData<Exception> paymentError;

    public PaymentViewModel(){
        paymentRepository = new PaymentRepository();
    }

    public LiveData<TokenResponse> getClientToken() {
        return paymentRepository.getClientToken();
    }

}