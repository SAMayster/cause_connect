package com.cause_connect.cause_c.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import com.cause_connect.cause_c.model.Admin;
import com.cause_connect.cause_c.repo.CauseRepo;


@RestController
@RequestMapping("/admin")

public class AdminController {
    
    @Autowired
    private CauseRepo crepo;
    
    @GetMapping("/addcause")
    public String AddCause(Model model){
        Admin addcause = new Admin();
        model.addAttribute("addcause", addcause);
        return addcause.toString();
    }

    
}
