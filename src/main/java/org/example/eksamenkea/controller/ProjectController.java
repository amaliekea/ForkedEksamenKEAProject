package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;

@Controller
public class ProjectController {
    private ProjectService projectService;
    private TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/project-leader-overview")
    public String showProjectLeaderOverview(HttpSession session, Model model) throws Errorhandling {

        Employee employee = (Employee) session.getAttribute("employee");
        List<ProjectEmployeeCostDTO> projects = projectService.getProjectsDTOByEmployeeId(employee.getEmployee_id()); // Hent projekter tilknyttet projektlederen
        System.out.println(projects);
        model.addAttribute("projects", projects); // Tilføj projekter til modellen, så de kan vises i HTML'en
        return "project-leader-overview";
    }

    @GetMapping("/project-leader-subproject-overview") // Amalie
    public String showProjectLeaderSubprojectOverview(@RequestParam("projectName") String projectName, HttpSession session, Model model) throws Errorhandling {
        int projectId = projectService.getProjectIdByProjectName(projectName);  // Hent projectId baseret på projectName
        List<Subproject> subprojects = projectService.getSubjectsByProjectId(projectId); // Henter subprojekter baseret på det fundne projectId
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectName", projectName);
        return "project-leader-subproject-overview"; // Returnerer view
    }


    @GetMapping("/add-project") //Amalie
    public String addNewProject(HttpSession session, Model model) throws Errorhandling {
        Project project = new Project();
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "user" fra sessionen.
        System.out.println("Employee ID: " + employee.getEmployee_id());
        model.addAttribute("project", project);
        model.addAttribute("employeeId", employee.getEmployee_id());
        return "add-project-form";
    }

    @PostMapping("/project-added") //Amalie
    public String addedProject(@ModelAttribute Project project) throws Errorhandling {
        projectService.addProject(project);
        return "redirect:/project-leader-overview";
    }


    @GetMapping("/worker-overview")
    public String showWorkerOverview(HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        List<Task> taskList = taskService.getTasklistByEmployeeId(employee.getEmployee_id());
        model.addAttribute("tasklist", taskList);

        return "worker-overview";
    }

    @GetMapping("/{projectName}/edit-project")
    public String getprojectToEdit(@PathVariable String projectName, Model model) throws Errorhandling {
        int projectId = projectService.getProjectIdByProjectName(projectName);
        Project project = projectService.getProjectFromProjectId(projectId);
        model.addAttribute("project", project);
        return "edit-project";
    }

    @PostMapping("/edit-project")
    public String editProject(@ModelAttribute Project project) throws Errorhandling {
        projectService.updateProject(project);
        return "redirect:/project-leader-subproject-overview?projectName=" + project.getProject_name();
    }

    @GetMapping("/archived-project-overview")
    public String showArchivedProjects(Model model) throws Errorhandling {
        List<Project> archivedProjects = projectService.getArchivedProjects(); // hent arkiverede projekter
        model.addAttribute("archivedProjects", archivedProjects); // Tilføj til model
        return "archived-project-overview";
    }

    @PostMapping("/archive-project")
    public String archiveProjectOverview(@RequestParam("projectName") String projectName, HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        int projectId = projectService.getProjectIdByProjectName(projectName);

        // Arkiver
        projectService.archiveProject(projectId);

        // Refresh listen af aktive projekter
        List<ProjectEmployeeCostDTO> projects = projectService.getProjectsDTOByEmployeeId(employee.getEmployee_id());
       // List<Project> projects = projectService.getProjectsByEmployeeId(employee.getEmployee_id());
        model.addAttribute("projects", projects);

        return "project-leader-overview";
    }

}
