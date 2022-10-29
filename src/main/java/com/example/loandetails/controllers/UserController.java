package com.example.loandetails.controllers;

import com.example.loandetails.model.Account;
import com.example.loandetails.model.AccountDetails;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private WebClient webClient;

    public UserController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/account")
    public Account createAccount(@RequestBody Account account) {
        LOGGER.info("This is great");

        LOGGER.info(account.getAccountId());
        account.getAccountDetails().forEach(ad -> {
            LOGGER.info(ad.getDetailId());
            LOGGER.info(ad.getDetailName());
        });

        return account;
    }//end of 'createAccount'

    @GetMapping("/test")
    public String testAccount(@RequestParam("account_id") String accountId,
                              @RequestParam("account_detail_id") String accountDetailId) {

        LOGGER.info(accountId);
        LOGGER.info(accountDetailId);

        AccountDetails ad = new AccountDetails();
        ad.setDetailId(accountDetailId);
        ad.setDetailName("Default");

        Account a = new Account();
        a.setAccountId(accountId);
        a.setAccountDetails(List.of(ad));

        String s = webClient.post()
                .uri("http://localhost:8090/api/account")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(a), Account.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return s;
    }//end of 'testAccount'

}//end of class
