package org.example.eksamenkea.controller;
import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/project")
@Controller
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) { //inversion of control ses ved konstruktør injection
        this.projectService = projectService;
    }

    @GetMapping("/project-leader-overview")//Zuhur
    public String showProjectLeaderOverview(HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        List<ProjectCostDTO> projects = projectService.getProjectsDTOByEmployeeId(employee.getEmployeeId());
        model.addAttribute("projects", projects); //tilføjer det til spring container
        return "project/project-leader-overview"; //retunerer vores view
    }

    @GetMapping("/add-project") //Amalie
    public String addNewProject(HttpSession session, Model model) {
        Project project = new Project();
        Employee employee = (Employee) session.getAttribute("employee");
        model.addAttribute("project", project);
        model.addAttribute("employeeId", employee.getEmployeeId()); //da et projekt har en fk employeeid skal dette sendes med
        return "project/add-project-form";
    }

    @PostMapping("/project-added") //Amalie
    public String addedProject(@ModelAttribute Project project) throws Errorhandling {
        projectService.addProject(project);
        return "redirect:/project/project-leader-overview";
    }

    @GetMapping("/{projectId}/edit-project") //Malthe
    public String getprojectToEdit(@PathVariable int projectId, Model model) throws Errorhandling {
        Project project = projectService.getProjectFromProjectId(projectId);
        model.addAttribute("project", project);
        return "project/edit-project";
    }

    @PostMapping("/edit-project") //Malthe
    public String editProject(@ModelAttribute Project project) throws Errorhandling {
        projectService.updateProject(project);
        return "redirect:/project/project-leader-overview";
    }

    @GetMapping("/archived-project-overview") //Zuhur
    public String showArchivedProjects(HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        List<ProjectCostDTO> archivedProjects = projectService.getArchivedProjects(employee.getEmployeeId());
        model.addAttribute("archivedProjects", archivedProjects);
        return "project/archived-project-overview";
    }

    @PostMapping("/archive-project") //Zuhur
    public String archiveProjectOverview(@RequestParam("projectId") int projectId) throws Errorhandling {
        projectService.archiveProject(projectId);
        return "redirect:/project/project-leader-overview";
    }
}
