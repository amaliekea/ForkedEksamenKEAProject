package org.example.eksamenkea.controller;
import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<ProjectEmployeeCostDTO> projects = projectService.getProjectsDTOByEmployeeId(employee.getEmployeeId());
        System.out.println(projects);
        model.addAttribute("projects", projects);
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
        System.out.println("Employee ID: " + employee.getEmployeeId());
        model.addAttribute("project", project);
        model.addAttribute("employeeId", employee.getEmployeeId());
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
        List<Task> taskList = taskService.getTasklistByEmployeeId(employee.getEmployeeId());
        model.addAttribute("tasklist", taskList);

        return "worker-overview";
    }

    @GetMapping("/{projectId}/edit-project")
    public String getprojectToEdit(@PathVariable int projectId, Model model) throws Errorhandling {
        Project project = projectService.getProjectFromProjectId(projectId);
        model.addAttribute("project", project);
        return "edit-project";
    }

    @PostMapping("/edit-project")
    public String editProject(@ModelAttribute Project project) throws Errorhandling {
        projectService.updateProject(project);
        return "redirect:/project-leader-subproject-overview?projectName=" + project.getProjectName();
    }

    @GetMapping("/archived-project-overview")
    public String showArchivedProjects(HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        List<ProjectEmployeeCostDTO> archivedProjects = projectService.getArchivedProjects(employee.getEmployeeId()); // hent arkiverede projekter
        model.addAttribute("archivedProjects", archivedProjects); // Tilføj til model
        return "archived-project-overview";
    }

    @PostMapping("/archive-project")
    public String archiveProjectOverview(@RequestParam("projectId") int projectId, HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");

        projectService.archiveProject(projectId);

        List<ProjectEmployeeCostDTO> projects = projectService.getProjectsDTOByEmployeeId(employee.getEmployeeId());
        model.addAttribute("projects", projects);

        return "project-leader-overview";
    }

}
