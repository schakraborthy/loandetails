package com.example.loandetails.model;

import lombok.Data;
import java.util.List;

@Data
public class Account {

    private List<CustomerDetails> customerDetails;

    private List<OfferDetails> offerDetails;

    @Override
    public String toString() {
        return "Account{" +
                "customerDetails=" + customerDetails +
                ", offerDetails=" + offerDetails +
                '}';
    }
}
