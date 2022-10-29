package com.example.loandetails.model;

import lombok.Data;

@Data
public class Payments {

    private String monthlyPayment;

    private String term;

    private String effectiveDate;

    @Override
    public String toString() {
        return "Payments{" +
                "monthlyPayment='" + monthlyPayment + '\'' +
                ", term='" + term + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                '}';
    }

}
