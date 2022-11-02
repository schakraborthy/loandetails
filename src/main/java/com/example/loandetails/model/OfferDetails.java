package com.example.loandetails.model;

import lombok.Data;

import java.util.List;

@Data
public class OfferDetails {

    private String quotetime;

    private String vin;

    private String vehicleDescription;

    private String year;

    private String make;

    private String model;

    private String style;

    private String state;

    private String county;

    private String city;

    private String disclosureText;

    private List<Payments> payments;

    @Override
    public String toString() {
        return "OfferDetails{" +
                "quotetime='" + quotetime + '\'' +
                ", vin='" + vin + '\'' +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", style='" + style + '\'' +
                ", state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", payments=" + payments +
                '}';
    }
}
