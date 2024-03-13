package com.cause_connect.cause_c.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cause_connect.cause_c.model.Donation;

public interface DonationRepo extends JpaRepository<Donation, Integer> {

   
    
    @Query("SELECT d FROM Donation d LEFT JOIN FETCH d.user LEFT JOIN FETCH d.cause")
    List<Donation> findAllWithUsersAndCauses();
}

    

