package com.cause_connect.cause_c.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cause_connect.cause_c.model.Cause;

public interface CauseRepo extends JpaRepository<Cause, Integer>{

    Cause findByName(String name);
    
}
