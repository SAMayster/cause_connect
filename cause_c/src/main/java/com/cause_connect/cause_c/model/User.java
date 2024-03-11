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
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int uid;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    


    
}
