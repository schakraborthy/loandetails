package com.example.loandetails.controllers;

import com.example.loandetails.model.Account;
import com.example.loandetails.model.CustomerDetails;
import com.example.loandetails.model.ReqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private WebClient webClient;

    public static final String itemString = "{ \"doctype\": \"Item\", \"enable_deferred_expense\": 0, \"country_of_origin\": \"United States\", \"is_sub_contracted_item\": 0, \"published_in_website\": 0, \"item_template\": \"\", \"create_variant\": 0, \"item_code\": \"%s\", \"item_name\": \"%s\", \"item_group\": \"Consumable\", \"valuation_rate\": null, \"standard_rate\": %s, \"description\": \"%s\", \"weight_per_unit\": 0, \"brand\": \"%s\", \"vin\": \"%s\", \"year\": 2022, \"make\": \"%s\", \"model\": \"%s\", \"style\": \"4dr Sdn Limited\", \"vehicle_description\": \"%s\", \"disclosure_text\": \"%s\", \"state\": \"%s\", \"country\": \"SANTA CLARA\", \"city\": \"%s\", \"vehicle_group_id\": \"null\", \"cust_rebates\": 400, \"rebate_code\": \"null\", \"residual\": %s, \"residualpct\": 57, \"orprogram\": \"%s\" }";

    public static final String leadString = "{ \"doctype\": \"Lead\", \"loan_type\": \"Financial Lease\", \"apply_for_credit_card\": \"No\", \"lead_owner\": \"Administrator\", \"status\": \"Lead\", \"type\": \"Client\", \"request_type\": \"Product Enquiry\", \"country\": \"United States\", \"enquiry_type\": \"Installment\", \"qualification_status\": \"Unqualified\", \"company\": \"Mastek\", \"language\": \"en\", \"disabled\": 0, \"unsubscribed\": 0, \"blog_subscriber\": 0, \"salutation\": \"\", \"first_name\": \"%s\", \"middle_name\": \"\", \"last_name\": \"\", \"age\": \"\", \"gender\": \"Male\", \"source\": \"Chatbot\", \"email_id\": \"%s\", \"mobile_no\": \"%s\", \"make\": \"%s\", \"model\": \"%s\", \"period\": \"%s\", \"effective_date\": \"%s\", \"net_income\": , \"monthly_payment\": %s, \"down_payment\": %s, \"residual_value\": %s , \"item\":%s}";

    public UserController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/lead")
    public Account createAccount(@RequestBody Account account) {

        String itemCode,itemName, name, description,brand,make,model,car, vin, state, city, period, email, effective_date, mobile, residual, vehicleDescription, disclosureText, downPayment, orProgram;
        int  term;
        double standardRate;

        //parse the necessary variables

        //item variables
        itemCode = Integer.toString(new Random().nextInt());
        itemName = account.getOfferDetails().getVin();
        standardRate = Math.round( Double.parseDouble(account.getOfferDetails().getPayments().get(0).getMonthlyPayment()) );
        term = Integer.parseInt(account.getOfferDetails().getPayments().get(0).getTerm());//for calculation of standard rate
        description = account.getOfferDetails().getVehicleDescription();
        make = account.getOfferDetails().getMake();
        model = account.getOfferDetails().getModel();
        brand = "Cello";
        vin = account.getOfferDetails().getVin();
        orProgram = account.getOfferDetails().getPayments().get(0).getOrProgram();
        city = account.getOfferDetails().getCity();
        state = account.getOfferDetails().getState();
        residual = account.getOfferDetails().getPayments().get(0).getResidual();
        vehicleDescription = account.getOfferDetails().getVehicleDescription();
        disclosureText = account.getOfferDetails().getDisclosureText();



        String completeItemString = String.format(itemString, itemCode, itemName, standardRate, description, brand, vin, make,model,vehicleDescription, disclosureText, state,city,residual,orProgram);


       // String.format(itemString,itemCode,itemName,standard_rate,"description", "brand");
       // String itemStringSend = String.format(itemString,"itemCode-123");
        String itemStringSend = itemString;
        //lead variables
        name = account.getCustomerDetails().getName();
        email = account.getCustomerDetails().getEmail();

        mobile = account.getCustomerDetails().getMobile();
        period = account.getOfferDetails().getPayments().get(0).getTerm();
        effective_date = account.getOfferDetails().getPayments().get(0).getEffectiveDate();
        downPayment = account.getOfferDetails().getPayments().get(0).getDownPayment();

        String completeLeadString = String.format(leadString, name, email, mobile, make, model, period, effective_date,account.getOfferDetails().getPayments().get(0).getMonthlyPayment(),downPayment,residual,itemCode );

        LOGGER.info(completeLeadString);

        MultipartBodyBuilder builderItem = new MultipartBodyBuilder();
        builderItem.part("doc", completeItemString);
        builderItem.part("action", "Save");

        String s = webClient.post()
                .uri("https://lmsvisheshatech.frappe.cloud/api/method/frappe.desk.form.save.savedocs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "token a17f634bf0da741:4555a0098b90c25")
                .header("Cookie", "full_name=Guest; sid=Guest; system_user=no; user_id=Guest; user_image=")
                .body(BodyInserters.fromMultipartData(builderItem.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();


        MultipartBodyBuilder builderLead = new MultipartBodyBuilder();
        builderLead.part("doc", completeLeadString);
        builderLead.part("action", "Save");

        String s2 = webClient.post()
                .uri("https://lmsvisheshatech.frappe.cloud/api/method/frappe.desk.form.save.savedocs")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "token a17f634bf0da741:4555a0098b90c25")
                .header("Cookie", "full_name=Guest; sid=Guest; system_user=no; user_id=Guest; user_image=")
                .body(BodyInserters.fromMultipartData(builderLead.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return account;
    }//end of 'createAccount'

}//end of class
