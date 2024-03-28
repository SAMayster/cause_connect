package com.cause_connect.cause_c.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.model.Donation;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.CauseRepo;
import com.cause_connect.cause_c.repo.UserRepo;
import com.cause_connect.cause_c.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping({ "" })
public class UserController {

    @Autowired
    private UserRepo urepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CauseRepo crepo;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Assuming User is your model class
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            User registeredUser = userService.registerUser(user);
            session.setAttribute("user", registeredUser); // Store user in session
            model.addAttribute("user", registeredUser); // Add user to the model
            return "redirect:/home/cause"; // return the name of the success page view
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Cause> causes = crepo.findAll();
            model.addAttribute("causes", causes);

            return "home";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // return the name of the login page view
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            session.setAttribute("user", loggedInUser); // Store user in session
            return "redirect:/home/cause";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/{userId}/history")
    public String getPaymentHistory(@PathVariable Integer userId, Model model) {
        User user = urepo.findById(userId).orElseThrow(); // This will throw an exception if the user is not found
        List<Donation> donations = user.getDonations();
        if (!donations.isEmpty()) {
            List<Map<String, Object>> donationHistory = donations.stream().map(donation -> {
                Map<String, Object> history = new HashMap<>();
                history.put("fullName", user.getFirstname() + " " + user.getLastname());
                history.put("causeName", donation.getCause().getName());
                history.put("payableAmount", donation.getPayableAmount());
                history.put("donationTime", donation.getDonationTime());
                return history;
            }).collect(Collectors.toList());
            model.addAttribute("donationHistory", donationHistory);
        } else {
            model.addAttribute("message", "No donations found for this user !!");
        }
        return "donationHistory"; 
    }

}

/*
 * @PostMapping("/register")
 * public ResponseEntity<User> registerUser(@RequestBody User user) {
 * return ResponseEntity.ok(userService.registerUser(user));
 * }
 */

/*
 * @PostMapping("/login")
 * public ResponseEntity<User> loginUser(@RequestBody User user) {
 * return ResponseEntity.ok(userService.loginUser(user.getEmail(),
 * user.getPassword()));
 * }
 */

/*
 * 
 * LEARNINGS :
 * 
 * 
 * 15/03/24:
 * 1) Since yesterday i was not able to view registration form in browser but
 * able to see on postman
 * it took me more than half day to understand or identify that my register.html
 * file was accepting user obj
 * but i was not passing in get mapping.
 * 
 * public String showRegistrationForm(Model model) {
 * model.addAttribute("user", new User()); (this was the change)
 * 
 * 2) @ModelAttribute User user , it takes user obj from registration form.
 * 
 * 14/03/24:
 * 1) I have not made the get mapping for /login, and was getting error in
 * frontend while backend was working
 * fine than I realised there is no get mapping for login.
 * 
 * 
 * 06/03/24:
 * import java.util.Data is used for both date and time but java.sql.Data is
 * used
 * to represent only Data it set 0 to hrs min and sec and .sql.Date one is from
 * jdbc
 */