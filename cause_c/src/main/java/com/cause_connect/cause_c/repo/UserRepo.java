package com.cause_connect.cause_c.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cause_connect.cause_c.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
    
}