package com.cause_connect.cause_c.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int aid;
    private String email;
    private String password;

    
}

/*private String cause_name;
 
    private double goalAmount;

    private String description;

    private MultipartFile imagFile; */