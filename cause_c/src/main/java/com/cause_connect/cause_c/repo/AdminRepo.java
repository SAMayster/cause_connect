package com.cause_connect.cause_c.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cause_connect.cause_c.model.Admin;

    public interface AdminRepo extends JpaRepository<Admin, Integer> {

        Admin findByEmailAndPassword(String email, String password);

        
    }
