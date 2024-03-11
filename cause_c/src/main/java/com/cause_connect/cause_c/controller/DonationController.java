package com.cause_connect.cause_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.service.DonationService;


@RestController
public class DonationController {



    @Autowired
    private DonationService donationservice;

    
    @PostMapping("/{causeName}/donation")
public ResponseEntity<?> addDonation(@PathVariable String causeName, @RequestBody Donation donation) {
    return donationservice.addDonation(causeName, donation);
}
}


    
    
    

