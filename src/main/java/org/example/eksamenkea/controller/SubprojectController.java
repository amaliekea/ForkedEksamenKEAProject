package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SubprojectController {
    private final SubprojectService subprojectService;

    public SubprojectController(SubprojectService subprojectService){
        this.subprojectService = subprojectService;
    }

    @GetMapping("/{subprojectName}/edit")
    public String getSubprojectToEdit(@PathVariable String subprojectName, Model model) throws Errorhandling {
        int subprojectId = subprojectService.getSubprojectIdBySubprojectName(subprojectName);
       Subproject subproject = subprojectService.getSubprojectBySubprojectId(subprojectId);
       model.addAttribute("subproject", subproject);
        return "edit-subproject";

    }

    @PostMapping("/edit-subproject")
    public String editSubproject(@ModelAttribute Subproject subproject) throws Errorhandling {
        subprojectService.updateSubproject(subproject);

        //TODO redirect til subproject-overview. Der skal laves en getProjectByProjectId-metode før det virker.
        return "redirect:/project-leader-overview";


    }

}
