package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Project> projects = projectService.getAllProjectsByEmployeeId(employee.getEmployee_id());  // Hent projekter tilknyttet projektlederen
        for (Project project : projects) {
            int employeeCost = projectService.calculateEmployeeCost(project); // Beregn medarbejderomkostninger
            project.setEmployee_cost(employeeCost); // Sæt omkostningerne på projektet
        }
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
        // Project project = projectService.getWorkerProjectFromEmployeeId(employee.getEmployee_id());
        List<Task> taskList = taskService.getTasklistByEmployeeId(employee.getEmployee_id());
        model.addAttribute("tasklist", taskList);
//        if (project != null) {
//            List<Subproject> subprojects = projectService.getSubjectsByProjectId(project.getProject_id());
//            model.addAttribute("project", project);
//            //model.addAttribute("employee", employee);
//            model.addAttribute("subprojects", subprojects);
//        }

        return "worker-overview";
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

        //Arkiver
        projectService.archiveProject(projectId);

        // Refresh listen af aktive projekter
        List<Project> projects = projectService.getAllProjectsByEmployeeId(employee.getEmployee_id());
        model.addAttribute("projects", projects);

        return "project-leader-overview";
    }

}
