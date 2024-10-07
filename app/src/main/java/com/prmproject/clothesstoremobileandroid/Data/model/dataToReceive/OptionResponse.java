package com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive;

public class OptionResponse {
    private int OptionId;
    private String Name;
    private long Price;

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int optionId) {
        OptionId = optionId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }
}
