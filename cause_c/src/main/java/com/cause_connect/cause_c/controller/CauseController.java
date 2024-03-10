package com.cause_connect.cause_c.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.repo.CauseRepo;

@RestController
@RequestMapping({"/cause"})
public class CauseController {
    
    @Autowired
    private CauseRepo crepo;

    @GetMapping({"","/"})
    public String showCauseList(Model model){
        List<Cause> cause=crepo.findAll();
        model.addAttribute("cause",cause);

        return cause.toString() ;
    }
    
}
