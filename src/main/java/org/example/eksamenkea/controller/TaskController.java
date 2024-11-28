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
//slet mig
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


    //DELETE-----------------------------------------------------------------
    //Vise bekræftelsessiden
    @GetMapping("/confirm-delete-task")
    public String showConfirmDeleteTask(@RequestParam("taskName") String taskName, @RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        //  hente taskId
        int taskId = taskService.getTaskIdByTaskName(taskName);

        //  taskId og subprojectName tilføjes til modellen
        model.addAttribute("taskId", taskId);
        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("taskName", taskName);
        return "confirm-delete-task";
    }


    // Metode til at slette task
    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam("taskName") String taskName, @RequestParam("subprojectName") String subprojectName,
            HttpSession session) throws Errorhandling {
        // Hent taskId baseret på taskName
        int taskId = taskService.getTaskIdByTaskName(taskName);

        // Hent employeeId fra session
        int employeeId = (int) session.getAttribute("employeeId");

        // Slet task baseret på taskId og employeeId
        taskService.deleteTaskById(taskId, employeeId);

        // Returner til Task Overview med subprojectName
        return "redirect:/project-leader-tasks?subprojectName=" + subprojectName;
    }
}
