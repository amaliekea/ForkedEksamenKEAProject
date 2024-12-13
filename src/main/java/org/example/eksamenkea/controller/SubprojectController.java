package org.example.eksamenkea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.SubprojectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class SubprojectController {
    private final SubprojectService subprojectService;
    private final ProjectService projectService;


    public SubprojectController(SubprojectService subprojectService, ProjectService projectService) {
        this.subprojectService = subprojectService;
        this.projectService = projectService;
    }

    @GetMapping("/edit-subproject") //Malthe
    public String getSubprojectToEdit(@RequestParam("subprojectId") int subprojectId, Model model) throws Errorhandling {
        Subproject subproject = subprojectService.getSubprojectBySubprojectId(subprojectId);
        model.addAttribute("subproject", subproject);
        return "edit-subproject";
    }


    @PostMapping("/edit-subproject") //malthe
    public String editSubproject(@ModelAttribute Subproject subproject) throws Errorhandling {
        subprojectService.updateSubproject(subproject);
        return "redirect:/project-leader-subproject-overview?projectId=" + subproject.getProjectId();
    }

    @GetMapping("/project-leader-subproject-overview") // Amalie
    public String showProjectLeaderSubprojectOverview(@RequestParam("projectId") int projectId, Model model) throws Errorhandling {
        Project project = projectService.getProjectFromProjectId(projectId);
        List<Subproject> subprojects = subprojectService.getSubjectsByProjectId(projectId);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("project", project);
        System.out.println("This is project-leader-subproject-overview with projectid" + projectId);
        return "project-leader-subproject-overview";
    }
    @ExceptionHandler(Errorhandling.class) //metoden skal håndterer undtagelser af typen 'Errorhandling'
    public String handleError(Model model, Exception exception, HttpServletRequest request) { //HttpServletRequest request indeholder information om HTTP-forespørgslen
        System.out.println("MESSAGE"+exception.getMessage());
        System.out.println("Exception handler kaldt: " + exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); //hentning af fejlkode
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "error/400";
            } else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
                return "error/502";
            }
        }
        return "error/error"; // Fallback error view
    }
}
