package com.cause_connect.cause_c.model;
import jakarta.persistence.Column;
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
    public class Cause {
        
         
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private int cid;
        
        private String name;

        @Column(columnDefinition="TEXT")
        private String description;
        private double goalAmount;
        private double amountRaised;


    }