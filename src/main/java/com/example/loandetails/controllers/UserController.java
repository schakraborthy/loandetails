package com.example.loandetails.controllers;

import com.example.loandetails.model.Account;
import com.example.loandetails.model.CustomerDetails;
import com.example.loandetails.model.ReqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

        LOGGER.info("Got the data {}", account);

        ReqHelper helper = new ReqHelper();
        helper.setDoctype("SUPER_DOC");
        helper.setLoan_type("SUPER_LOAN_TYPE");
        helper.setStatus("SUPER_STATUS");
        helper.setType("SUPER_TYPE");

        String s = webClient.post()
                .uri("http://localhost:8090/api/account")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(helper), ReqHelper.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return account;
    }//end of 'createAccount'

}//end of class
