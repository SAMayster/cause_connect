package com.cause_connect.cause_c.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonationHistory {
    private String fullName;
    private String causeName;
    private double payableAmount;
    private double goalAmount;
    private Date donationTime;
    
}
