package com.cause_connect.cause_c.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.model.DonationHistory;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.UserRepo;

@RestController
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private UserRepo urepo;


    @GetMapping("/{userId}/history")
public ResponseEntity<?> getPaymentHistory(@PathVariable Integer userId) {
    Optional<User> userOptional = urepo.findById(userId);
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        List<Donation> donations = user.getDonations();
        if (!donations.isEmpty()) {
            List<DonationHistory> donationHistory = donations.stream().map(donation -> {
                DonationHistory history = new DonationHistory();
                history.setFullName(user.getFirstname() + " " + user.getLastname());
                history.setCauseName(donation.getCause().getName());
                history.setPayableAmount(donation.getPayableAmount());
                history.setGoalAmount(donation.getCause().getGoalAmount());
                history.setDonationTime(donation.getDonationTime());
                return history;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(donationHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No donations found for this user", HttpStatus.NOT_FOUND);
        }
    } else {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
    
}
/*
 import java.util.Data is used for both date and time but java.sql.Data is used 
 to represent only Data it seyt 0 to hrs min and sec and .sql.Date one is from jdbc 
 */