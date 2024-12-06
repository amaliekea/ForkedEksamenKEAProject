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

import java.util.List;

@Controller
public class SubprojectController {
    private final SubprojectService subprojectService;
    private final ProjectService projectService;

    public SubprojectController(SubprojectService subprojectService, ProjectService projectService){
        this.subprojectService = subprojectService;
        this.projectService = projectService;
    }

    @GetMapping("/edit-subproject")
    public String getSubprojectToEdit(@RequestParam("subprojectId") int subprojectId, Model model) throws Errorhandling {
        Subproject subproject = subprojectService.getSubprojectBySubprojectId(subprojectId);
        model.addAttribute("subproject", subproject);
        return "edit-subproject";
    }


    @PostMapping("/edit-subproject")
    public String editSubproject(@ModelAttribute Subproject subproject) throws Errorhandling {
        subprojectService.updateSubproject(subproject);
        Project project = projectService.getProjectFromProjectId(subproject.getProjectId());
        String projectName = project.getProjectName();
        return "redirect:/project-leader-subproject-overview?projectId=" + subproject.getProjectId();
    }

    @GetMapping("/project-leader-subproject-overview") // Amalie
    public String showProjectLeaderSubprojectOverview(@RequestParam("projectId") int projectId, HttpSession session, Model model) throws Errorhandling {
        Project project = projectService.getProjectFromProjectId(projectId);
        List<Subproject> subprojects = projectService.getSubjectsByProjectId(projectId);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectId", projectId); // Send projectId
        model.addAttribute("projectName", project.getProjectName());
        return "project-leader-subproject-overview"; //  view
    }

}
