package com.cause_connect.cause_c.controller;

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
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.service.DonationService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class DonationController {

    @Autowired
    private DonationService donationservice;

    
    @GetMapping("/donate")
    public String showDonationForm(Model model) {
    model.addAttribute("donation", new Donation());
    return "donate"; // Name of the Thymeleaf view for the donation form
}

@PostMapping("/donate")
public ModelAndView addDonation(@RequestParam String causeName, @RequestParam String userId, @ModelAttribute Donation donation, HttpServletRequest request) {
    // Get the user from the session
    User user = (User) request.getSession().getAttribute("user");
    System.out.println("User in session: " + user);

    ModelAndView modelAndView = new ModelAndView();

    if (user == null) {
        // If the user is not logged in, redirect to the login page
        modelAndView.addObject("message", "Please log in to make a donation");
        modelAndView.setViewName("redirect:/login");
    } else {
        // If the user is logged in, proceed with the donation
        donation.setUser(user);
        ResponseEntity<?> response = donationservice.addDonation(causeName, donation, Integer.parseInt(userId));

        if (response.getStatusCode() == HttpStatus.CREATED) {
            modelAndView.addObject("message", "Thank you for your donation!");
        } else {
            modelAndView.addObject("message", response.getBody().toString());
        }

        modelAndView.setViewName("donationstatus");
    }

    return modelAndView;
}



    
//     @PostMapping("/donate")
// public String addDonation(@ModelAttribute Donation donation, HttpServletRequest request, Model model) {
//     User user = getUserFromSession(request);
//     Cause cause = getCauseFromSession(request);

//     if (user == null || cause == null) {
//         model.addAttribute("message", "Please log in to make a donation");
//         return "redirect:/login";
//     } else {
//         ResponseEntity<?> response = donationservice.addDonation(cause.getName(), donation, user.getUid());
//         if (response.getStatusCode() == HttpStatus.CREATED) {
//             model.addAttribute("message", "Thank you for your donation!");
//             model.addAttribute("donation", response.getBody());
//         } else {
//             model.addAttribute("message", response.getBody().toString());
//         }
//         return "donationstatus";
//     }
// }

// private User getUserFromSession(HttpServletRequest request) {
//     return (User) request.getSession().getAttribute("user");
// }

// private Cause getCauseFromSession(HttpServletRequest request) {
//     return (Cause) request.getSession().getAttribute("cause");
// }


    
    


    
    
    
}



/*
 *  @PostMapping("/cause/{causeName}/donation/{userId}")
    public ResponseEntity<?> addDonation(@PathVariable String causeName, @RequestBody Donation donation, @PathVariable Integer userId) {
        return donationservice.addDonation(causeName, donation, userId);
    }
 */
    
    
    

