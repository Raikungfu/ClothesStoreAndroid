package com.prmproject.clothesstoremobileandroid.Data.model.dataToSend;

public class VnPaymentRequestModel {
    private Integer bookingId;
    private String fullName;
    private String description;
    private Double totalPrice;
    private String createDate;
    private Integer VnPayMethod;

    public VnPaymentRequestModel(Integer bookingId, Double totalPrice, Integer vnPayMethod) {
        this.bookingId = bookingId;
        this.totalPrice = totalPrice;
        this.VnPayMethod = vnPayMethod;
    }
}
