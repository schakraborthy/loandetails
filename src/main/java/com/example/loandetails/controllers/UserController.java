package com.example.loandetails.controllers;

import com.example.loandetails.model.Account;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/account")
    public String createAccount(@RequestBody Account account) {
        LOGGER.info("This is great");

        LOGGER.info(account.getAccountId());
        account.getAccountDetails().forEach(ad -> {
            LOGGER.info(ad.getDetailId());
            LOGGER.info(ad.getDetailName());
        });

        return "Done";
    }//end of 'createAccount'

}//end of class
