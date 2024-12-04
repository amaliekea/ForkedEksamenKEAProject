package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.EmployeeService;
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
    private final EmployeeService employeeService;

    public TaskController(TaskService taskService, SubprojectService subprojectService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
        this.employeeService = employeeService;
    }

    //CREATE--------------------------------------------------------------
    @GetMapping("/add-task")
    public String addTask(@RequestParam("subprojectName") String subprojectName, HttpSession session, Model model) throws Errorhandling {
        Task task = new Task();
        Employee employee = (Employee) session.getAttribute("employee");  // Henter "user" fra sessionen.
        int subprojectId = subprojectService.getSubprojectIdBySubprojectName(subprojectName);

        model.addAttribute("task", task);
        model.addAttribute("employeeId", employee.getEmployeeId());
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

        //Hent liste af employees
        List<Employee> employeeList = employeeService.getAllWorkers();

        // Tilføj tasks og subprojectName til modellen
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("employeeList", employeeList);

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
        System.out.println("Received Actual Hours: " + task.getActualHours());
        taskService.updateTask(task);
        return "redirect:/worker-overview";
    }


    @PostMapping("/assign-worker")
    public String assignEmployeeToTask(@RequestParam("subprojectName") String subprojectName, @RequestParam("taskName") String taskName, @RequestParam ("employeeEmail") String employeeEmail, Model model, HttpSession session) throws Errorhandling {
        Employee employee = employeeService.getEmployeeByEmail(employeeEmail);
        employee.setEmployeeId(employee.getEmployeeId());
        int taskId = taskService.getTaskIdByTaskName(taskName);
        int taskHours = taskService.getTaskByName(taskName).getEstimatedHours();
        List<Task> tasklist = taskService.getTasklistByEmployeeId(employee.getEmployeeId());
        int totalTaskHours = 0;

        for (Task task : tasklist) {
            totalTaskHours += task.getEstimatedHours();
        }

        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("taskId", taskId);
        model.addAttribute("totalTaskHours", totalTaskHours);
        model.addAttribute("employee", employee);

        if ((totalTaskHours + taskHours) <= employee.getMaxHours()) {
            taskService.assignEmployeeToTask(taskId, employee.getEmployeeId());
        } else {
            return "error/error-exceed-max-hours-for-worker";
        }
        return "redirect:/project-leader-tasks?subprojectName=" + subprojectName;
    }


}
