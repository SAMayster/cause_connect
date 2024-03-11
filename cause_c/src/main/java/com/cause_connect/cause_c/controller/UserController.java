package com.cause_connect.cause_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cause_connect.cause_c.repo.UserRepo;

@RestController
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private UserRepo urepo;

    
}
