package com.cause_connect.cause_c.model;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class Admin {
    
    private String cause_name;
 
    private double goalAmount;

    private String description;

    private MultipartFile imagFile;
}

