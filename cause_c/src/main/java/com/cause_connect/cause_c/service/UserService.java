package com.cause_connect.cause_c.service;

import com.cause_connect.cause_c.model.Admin;
import com.cause_connect.cause_c.model.User;
import com.cause_connect.cause_c.repo.AdminRepo;
import com.cause_connect.cause_c.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminRepo adminRepo;

    private Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public User registerUser(User user) throws Exception {
        // Check if the email is valid
        if (!emailPattern.matcher(user.getEmail()).matches()) {
            throw new Exception("Invalid email format");
        }

        // Check if the email is already in use
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email already in use");
        }

        // Save the user to the database
        return userRepo.save(user);
    }

    public User loginUser(String email, String password) {
        // Fetch the user from the database
        return userRepo.findByEmailAndPassword(email, password);
    }

    public Admin loginAdmin(String email, String password) {
        // Fetch the admin from the database
        return adminRepo.findByEmailAndPassword(email, password);
    }
}


