package com.cause_connect.cause_c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.repo.CauseRepo;
import com.cause_connect.cause_c.repo.DonationRepo;

@Service
public class DonationService {
    @Autowired
    private CauseRepo crepo;

    @Autowired
    private DonationRepo drepo;

    public ResponseEntity<?> addDonation(String causeName, Donation donation) {
        Cause cause = crepo.findByName(causeName);
        if (cause != null) {
            if (donation.getPayableAmount() <= cause.getGoalAmount()) {
                cause.setAmountRaised(cause.getAmountRaised() + donation.getPayableAmount());
                donation.setCause(cause);
                crepo.save(cause);
                donation.setAmountRaisedTillNow(cause.getAmountRaised());
                drepo.save(donation); // Saving the donation to the repo
                
                

                return new ResponseEntity<>(donation, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Donation amount exceeds goal amount", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Cause not found", HttpStatus.NOT_FOUND);
        }
    }
}

    /* here .set for donation.setCause(cause); is done beacuse you just have ciause name as pathvariable not 
     * cause obj so you have to set cause obj to donayion.
      */

