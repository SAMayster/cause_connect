package com.cause_connect.cause_c.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.DonationDto;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.CauseRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "/home" ,""})
public class CauseController {

    @Autowired
    private CauseRepo crepo;

    @GetMapping("")
    public String home(Model model) {
        List<Cause> causes = crepo.findAll(); // Fetch the causes from your service
        model.addAttribute("causes", causes);
        return "home";
    }
    
    
    @GetMapping("/cause")
    public String showCauseList(Model model) {

        List<Cause> causes = crepo.findAll();
        model.addAttribute("causes", causes);
        return "cause";  // This should be the name of your Thymeleaf template
    }

 

    

    @GetMapping("/cause/details")
    public String showCauseDetails(@RequestParam String name, Model model, HttpSession session) {
        Cause cause = crepo.findByName(name);
        DonationDto donationDto = new DonationDto();
        User user = (User) session.getAttribute("user");
    
        donationDto.setName(cause.getName());
        if (user != null) {
            donationDto.setUser(user.getUid());
        }
        
        model.addAttribute(donationDto);
        model.addAttribute("cause", cause);
        return "causedetails";  // This should be the name of your Thymeleaf template
    }



}




/*
 Learnings:
15/03/23
1) Done very basic mistake in mappings , i changed html file name and than started havings error
2)used wrong names in causedetails.html like used cause.id instead of caused.cid , due to which not 
able to show donation button
 */