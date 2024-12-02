package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SubprojectController {
    private final SubprojectService subprojectService;
    private final ProjectService projectService;

    public SubprojectController(SubprojectService subprojectService, ProjectService projectService){
        this.subprojectService = subprojectService;
        this.projectService = projectService;
    }

    @GetMapping("/{subprojectName}/edit-subproject")
    public String getSubprojectToEdit(@PathVariable String subprojectName, Model model) throws Errorhandling {
        int subprojectId = subprojectService.getSubprojectIdBySubprojectName(subprojectName);
       Subproject subproject = subprojectService.getSubprojectBySubprojectId(subprojectId);
       model.addAttribute("subproject", subproject);
        return "edit-subproject";

    }

    @PostMapping("/edit-subproject")
    public String editSubproject(@ModelAttribute Subproject subproject) throws Errorhandling {
        subprojectService.updateSubproject(subproject);
        Project project = projectService.getProjectFromProjectId(subproject.getProject_id());
        String projectName = project.getProject_name();

        return "redirect:/project-leader-subproject-overview?projectName=" + projectName;
    }

}
