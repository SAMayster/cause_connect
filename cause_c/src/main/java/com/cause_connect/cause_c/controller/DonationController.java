package com.cause_connect.cause_c.controller;

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
import com.cause_connect.cause_c.service.DonationService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class DonationController {

    @Autowired
    private DonationService donationservice;

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
public String addDonation(@ModelAttribute DonationDto donationDto) {
    Donation donation = new Donation();
    User user = urepo.findById(donationDto.getUser()).get();
    Cause cause = crepo.findByName(donationDto.getName());
    donation.setPayableAmount(donationDto.getAmount());
    donation.setUser(user);
    donation.setCause(cause);
    drepo.save(donation);
    return "donationstatus";
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
    
    
    

