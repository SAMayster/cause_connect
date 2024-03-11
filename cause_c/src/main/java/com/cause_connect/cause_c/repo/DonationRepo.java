package com.cause_connect.cause_c.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cause_connect.cause_c.model.Donation;

public interface DonationRepo extends JpaRepository<Donation, Integer> {
    
}
