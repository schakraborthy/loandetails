package com.example.loandetails.model;

import lombok.Data;

@Data
public class Payments {

    private String monthlyPayment;

    private String term;

    private String effectiveDate;

    private String residual;

    private String downPayment;

    private String orProgram;

    @Override
    public String toString() {
        return "Payments{" +
                "monthlyPayment='" + monthlyPayment + '\'' +
                ", term='" + term + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                '}';
    }

}
