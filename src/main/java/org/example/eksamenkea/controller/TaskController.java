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
    private final SubprojectService subprojectService;

    public TaskController(TaskService taskService, SubprojectService subprojectService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
    }

    //CREATE--------------------------------------------------------------
    @GetMapping("/add-task")
    public String addTask(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        Task task = new Task();
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "user" fra sessionen.
        int subprojectId = subprojectService.getSubprojectIdBySubprojectName(subprojectName);

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

    //READ------------------------------------------------------------------
    @GetMapping("/project-leader-tasks")
    public String getTaskBySubprojectName(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {

        // Hent subproject ID baseret på subproject name
        int subprojectId = subprojectService.getSubprojectIdBySubprojectName(subprojectName);

        // Hent tasks for det fundne subproject ID
        List<Task> tasks = taskService.getTaskBySubprojectId(subprojectId);

        // Tilføj tasks og subprojectName til modellen
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectName", subprojectName);

        return "project-leader-task-overview";
    }

    //UPDATE------------------------------------------------------------------
    @GetMapping("/task-status")
    public String updateTaskStatus(@RequestParam("taskName") String taskName, HttpSession session, Model model) throws Errorhandling {
        model.addAttribute("task", taskService.getTaskByName(taskName));
        return "task-edit-status";
    }

    @PostMapping("/task-status")
    public String updatedTask(@ModelAttribute Task task) throws Errorhandling {
        taskService.updateTask(task);
        return "redirect:/worker-overview";
    }

    //DELETE-----------------------------------------------------------------
    // Metode til at markere en task som "Complete" og arkivere den
    @PostMapping("/mark-task-complete")
    public String markTaskAsComplete(@RequestParam("taskName") String taskName,
                                     @RequestParam("subprojectName") String subprojectName) throws Errorhandling {
        taskService.markTaskAsComplete(taskName, subprojectName);
        return "redirect:/project-leader-tasks?subprojectName=" + subprojectName;
    }

}
