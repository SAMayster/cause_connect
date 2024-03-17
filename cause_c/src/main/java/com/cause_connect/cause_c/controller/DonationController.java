package com.cause_connect.cause_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.service.DonationService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class DonationController {

    @Autowired
    private DonationService donationservice;

    

    @PostMapping("/donate")
    public ResponseEntity<?> addDonation(@ModelAttribute Donation donation, HttpServletRequest request) {
        // Get the user from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            // If the user is not logged in, redirect to the login page
            return new ResponseEntity<>("Please log in to make a donation", HttpStatus.UNAUTHORIZED);
        } else {
            // If the user is logged in, proceed with the donation
            return donationservice.addDonation(donation.getCause().getName(), donation, user.getUid());
        }
    }
}



/*
 *  @PostMapping("/cause/{causeName}/donation/{userId}")
    public ResponseEntity<?> addDonation(@PathVariable String causeName, @RequestBody Donation donation, @PathVariable Integer userId) {
        return donationservice.addDonation(causeName, donation, userId);
    }
 */
    
    
    

