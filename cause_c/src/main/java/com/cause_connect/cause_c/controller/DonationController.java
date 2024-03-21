package com.cause_connect.cause_c.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.model.DonationDto;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.CauseRepo;
import com.cause_connect.cause_c.repo.DonationRepo;
import com.cause_connect.cause_c.repo.UserRepo;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DonationController {

   

    @Autowired
    private CauseRepo crepo;

    @Autowired
    private UserRepo urepo;

    @Autowired
    private DonationRepo drepo;

    @GetMapping("/donate")
    public String showDonationForm(Model model) {

        model.addAttribute("donation", new Donation());
        return "causedetails"; // Name of the Thymeleaf view for the donation form
    }

    @PostMapping("/donate")
    public String addDonation(@ModelAttribute DonationDto donationDto, HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            // User is not logged in, redirect to home page
            return "redirect:/home";
        }
    
        // User is logged in, proceed with donation
        Donation donation = new Donation();
        User user = urepo.findById(donationDto.getUser()).get();
        Cause cause = crepo.findByName(donationDto.getName());

        // Check if the goal amount has been reached or exceeded
        if (cause.getAmountRaised() >= cause.getGoalAmount()) {
            model.addAttribute("goalReachedMessage", "The cause has already raised the required amount. No further donations are needed.");
            return "donationstatus";
        }

        // Check if donation amount exceeds goal amount
        if (donationDto.getAmount() > cause.getGoalAmount()) {
            model.addAttribute("donationDto", donationDto);
            model.addAttribute("cause", cause);
            return "donationstatus"; // Return to the donation status page
        }
    
        // Update the amount raised for the cause
        cause.setAmountRaised(cause.getAmountRaised() + donationDto.getAmount());
        cause.setLastDonationDate(new Date());
        crepo.save(cause); // Save the updated cause
    
        donation.setPayableAmount(donationDto.getAmount());
        donation.setUser(user);
        donation.setCause(cause);
        donation.setAmountRaisedTillNow(cause.getAmountRaised());
        drepo.save(donation);

        if (cause.getAmountRaised() >= cause.getGoalAmount()) {
            model.addAttribute("goalReachedMessage", "Congratulations! The cause has raised the required amount.");
        }
    
        model.addAttribute("donationDto", donationDto);
        model.addAttribute("cause", cause);
        return "donationstatus";
    }
    

    // @PostMapping("/donate")
    // public String addDonation(@ModelAttribute Donation donation,
    // HttpServletRequest request, Model model) {
    // User user = getUserFromSession(request);
    // Cause cause = getCauseFromSession(request);

    // if (user == null || cause == null) {
    // model.addAttribute("message", "Please log in to make a donation");
    // return "redirect:/login";
    // } else {
    // ResponseEntity<?> response = donationservice.addDonation(cause.getName(),
    // donation, user.getUid());
    // if (response.getStatusCode() == HttpStatus.CREATED) {
    // model.addAttribute("message", "Thank you for your donation!");
    // model.addAttribute("donation", response.getBody());
    // } else {
    // model.addAttribute("message", response.getBody().toString());
    // }
    // return "donationstatus";
    // }
    // }

    // private User getUserFromSession(HttpServletRequest request) {
    // return (User) request.getSession().getAttribute("user");
    // }

    // private Cause getCauseFromSession(HttpServletRequest request) {
    // return (Cause) request.getSession().getAttribute("cause");
    // }

}

/*
 * @PostMapping("/cause/{causeName}/donation/{userId}")
 * public ResponseEntity<?> addDonation(@PathVariable String
 * causeName, @RequestBody Donation donation, @PathVariable Integer userId) {
 * return donationservice.addDonation(causeName, donation, userId);
 * }
 */
