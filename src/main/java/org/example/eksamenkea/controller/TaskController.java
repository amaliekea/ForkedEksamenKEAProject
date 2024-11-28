package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add-task")
    public String addTask(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        Task task = new Task();
        Role userRole = (Role) session.getAttribute("userRole");  // Henter "userrole" fra sessionen.
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "user" fra sessionen.
        int subprojectId = taskService.getSubprojectIdBySubprojectName(subprojectName);
        System.out.println("subprojectid !!!"+subprojectId);

        if (userRole == Role.PROJECTLEADER) {
            model.addAttribute("task", task);
            model.addAttribute("employeeId", employee.getEmployee_id());
            model.addAttribute("subprojectId", subprojectId);
            return "add-task";
        }
        throw new Errorhandling("cant add task");
    }

    @PostMapping("/task-added") //Amalie
    public String addedTask(@ModelAttribute Task task) throws Errorhandling {
        System.out.println("subproject_id: " + task.getSubproject_id());
        taskService.createTask(task);
        return "redirect:/project-leader-tasks";
    }

    @GetMapping("/project-leader-tasks")
    public String getTaskBySubprojectName(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        logger.info("Fetching tasks for subproject: {}", subprojectName); //kan denne slettes?

        // Hent brugerens rolle fra sessionen
        Role employeeRole = (Role) session.getAttribute("userRole");
        if (employeeRole != Role.PROJECTLEADER) {
            logger.warn("Access denied for user role: {}", employeeRole);
            return "error/error";
        }

        try {
            // Hent subproject ID baseret på subproject name
            int subprojectId = taskService.getSubprojectIdBySubprojectName(subprojectName);

            // Hent tasks for det fundne subproject ID
            List<Task> tasks = taskService.getTaskBySubprojectId(subprojectId);

            // Tilføj tasks og subprojectName til modellen
            model.addAttribute("tasks", tasks);
            model.addAttribute("subprojectName", subprojectName);

            return "project-leader-task-overview";
        } catch (Errorhandling e) {
            logger.error("Error fetching tasks: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "error/error";
        }
    }

}
