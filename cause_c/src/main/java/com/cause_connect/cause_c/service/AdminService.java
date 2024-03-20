package com.cause_connect.cause_c.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.model.DonationHistory;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.CauseRepo;
import com.cause_connect.cause_c.repo.DonationRepo;
import com.cause_connect.cause_c.repo.UserRepo;

@Service
public class AdminService {

    @Autowired
    CauseRepo crepo;

    @Autowired
    UserRepo urepo;

    @Autowired
    DonationRepo drepo;

    public String addCause(Cause cause) {
        try {
            crepo.save(cause);
            return "Cause added successfully";
        } catch (DataIntegrityViolationException e) {
            // Handle the case where saving the cause would violate a database constraint
            return "Data integrity violation";
        } catch (Exception e) {
            // Handle any other errors that might occur
            return "An error occurred";
        }
    }

    public String deleteCause(int id) {
        Cause cause = crepo.findById(id);

        if (cause != null) {
            crepo.delete(cause);
            return "Deleted!!";
        } else {
            return "Not Deleted!!";
        }
    }


    public List<Map<String, Object>> getAllUsersPaymentHistory() {
        List<User> users = urepo.findAll();
        List<Map<String, Object>> allUsersDonationHistory = new ArrayList<>();

        for (User user : users) {
            List<Donation> donations = user.getDonations();
            if (!donations.isEmpty()) {
                List<Map<String, Object>> userDonationHistory = donations.stream().map(donation -> {
                    Map<String, Object> history = new HashMap<>();
                    history.put("fullName", user.getFirstname() + " " + user.getLastname());
                    history.put("causeName", donation.getCause().getName());
                    history.put("payableAmount", donation.getPayableAmount());
                    history.put("donationTime", donation.getDonationTime());
                    return history;
                }).collect(Collectors.toList());
                allUsersDonationHistory.addAll(userDonationHistory);
            }
        }


        return allUsersDonationHistory;
    }
}
