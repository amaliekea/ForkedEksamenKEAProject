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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class TaskController {
    private final TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/project-leader-tasks")
    public String getTaskBySubprojectId(@RequestParam("subproject_id") int subprojectId,
                                        @RequestParam("subprojectName") String subprojectName,
                                        HttpSession session, Model model) throws Errorhandling {
        logger.info("Fetching tasks for subproject ID: {}", subprojectId);

        // Henter brugerens rolle fra sessionen
        Role employeeRole = (Role) session.getAttribute("userRole");
        if (employeeRole != Role.PROJECTLEADER) {
            logger.warn("Access denied for user role: {}", employeeRole);
            return "error/error";
        }

        try {
            // Henter tasks for det angivne subproject_id
            List<Task> tasks = taskService.getTaskBySubprojectId(subprojectId);
            model.addAttribute("tasks", tasks); // Tilføjer listen af tasks til modellen
            model.addAttribute("subprojectName", subprojectName); // Tilføjer subprojectName til modellen
            return "project-leader-task-overview";
        } catch (Errorhandling e) {
            logger.error("Error fetching tasks: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "error/error";
        }
    }
//    @GetMapping("/worker-overview-task")
//    public String showWorkerOverview(HttpSession session, Model model) throws Errorhandling {
//        Role employeeRole = (Role) session.getAttribute("userRole");
//        Employee employee = (Employee) session.getAttribute("employee");
//
//        System.out.println("Employee in session: " + session.getAttribute("employee"));
//        System.out.println("UserRole in session: " + session.getAttribute("userRole"));
//        System.out.println(employee.getEmployee_id());
//
//        if (employeeRole == Role.WORKER) {
//            List<Task> taskslist = taskService.getTaskBySubprojectId(employee.getEmployee_id());
//            model.addAttribute("tasklist", taskslist);
//
//            return "worker-overview";
//        }
//        throw new Errorhandling("User is not authorized to view this page.");
//    }
}
