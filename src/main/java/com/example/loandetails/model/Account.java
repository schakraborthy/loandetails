package com.example.loandetails.model;

import lombok.Data;
import java.util.List;

@Data
public class Account {

    private String accountId;

    private List<AccountDetails> accountDetails;

}
