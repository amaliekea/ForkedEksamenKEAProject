package org.example.eksamenkea.controller;

import org.example.eksamenkea.service.SubprojectService;
import org.springframework.stereotype.Controller;

@Controller
public class SubprojectController {
    private final SubprojectService subprojectService;

    public SubprojectController(SubprojectService subprojectService){
        this.subprojectService = subprojectService;
    }

}
