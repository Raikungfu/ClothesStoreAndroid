package com.prmproject.clothesstoremobileandroid.Data.model.type;

public enum VnPayMethod {
    ATM(0),
    QRCode(1),
    CreditCard(2);

    private final int value;

    VnPayMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
