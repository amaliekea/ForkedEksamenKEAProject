package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.SubprojectService;
import org.example.eksamenkea.service.TaskService;
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

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add-task")
    public String addTask(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        Task task = new Task();
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "user" fra sessionen.
        int subprojectId = taskService.getSubprojectIdBySubprojectName(subprojectName);

            model.addAttribute("task", task);
            model.addAttribute("employeeId", employee.getEmployee_id());
            model.addAttribute("subprojectId", subprojectId);
            model.addAttribute("subprojectName", subprojectName);
            return "add-task";

    }

    @PostMapping("/task-added") //Amalie
    public String addedTask(@RequestParam("subprojectName") String subprojectName, @ModelAttribute Task task) throws Errorhandling {
        taskService.createTask(task);
        return "redirect:/project-leader-tasks?subprojectName=" + subprojectName;
    }

    @GetMapping("/project-leader-tasks")
    public String getTaskBySubprojectName(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {

            // Hent subproject ID baseret på subproject name
            int subprojectId = taskService.getSubprojectIdBySubprojectName(subprojectName);

            // Hent tasks for det fundne subproject ID
            List<Task> tasks = taskService.getTaskBySubprojectId(subprojectId);

            // Tilføj tasks og subprojectName til modellen
            model.addAttribute("tasks", tasks);
            model.addAttribute("subprojectName", subprojectName);

            return "project-leader-task-overview";
    }

}
