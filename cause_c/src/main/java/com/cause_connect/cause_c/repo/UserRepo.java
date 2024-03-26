package com.cause_connect.cause_c.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cause_connect.cause_c.model.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>{
    Optional<User> findById(Integer userId);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    
}

















/*
LEARNINGS :
optional is used if user exists than it will return that details and if not exists will 
return null but will not give error and with logic in controller we can handle that */