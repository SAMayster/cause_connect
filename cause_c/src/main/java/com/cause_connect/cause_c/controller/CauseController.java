package com.cause_connect.cause_c.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cause_connect.cause_c.model.Cause;
import com.cause_connect.cause_c.repo.CauseRepo;

@RestController
@RequestMapping({ "/cause" })
public class CauseController {

    @Autowired
    private CauseRepo crepo;

    @GetMapping({""})
public List<String> showCauseList() {
    List<Cause> causes = crepo.findAll();
    List<String> causeNames = new ArrayList<>();
    for (Cause cause : causes) {
        causeNames.add(cause.getName());
    }
    return causeNames;
}

    @GetMapping({"/{name}"})
    public Cause showCauseDetails(@PathVariable String name) {
        return crepo.findByName(name);
    }

}
