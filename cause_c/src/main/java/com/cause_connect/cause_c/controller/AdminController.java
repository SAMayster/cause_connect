package com.cause_connect.cause_c.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.repo.CauseRepo;


@RestController
@RequestMapping("/admin")

public class AdminController {
    
    @Autowired
    private CauseRepo crepo;
    

    
    @PostMapping("/addcause")
    public ResponseEntity<Cause> addCause(@RequestBody Cause cause) {
        try {
            Cause savedCause = crepo.save(cause);
            return new ResponseEntity<>(savedCause, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle the case where saving the cause would violate a database constraint
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle any other errors that might occur
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deletecause/{name}")
public ResponseEntity<String> deleteCause(@PathVariable String name) {
    Cause cause = crepo.findByName(name);
    if (cause != null) {
        crepo.delete(cause);
        return new ResponseEntity<>("Cause deleted successfully!", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Cause not found!", HttpStatus.NOT_FOUND);
    }
}


/*
 @PostMapping("/addcause")
    public String addCause(@RequestBody Cause cause) {
         crepo.save(cause);
         return "Cause created successfully!";
    }


    @DeleteMapping("/deletecause/{name}")
    public String deleteCause(@PathVariable String name) {
        Cause cause = crepo.findByName(name);
        crepo.delete(cause);
        return "Cause deleted successfully!";
    }
 */

    
}
