package com.example.loandetails.model;

import lombok.Data;

@Data
public class CustomerDetails {

    private String name;

    private String email;

    private String mobile;

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
