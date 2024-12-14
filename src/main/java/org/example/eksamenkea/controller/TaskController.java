package org.example.eksamenkea.controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenkea.model.*;
import org.example.eksamenkea.service.EmployeeService;
import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.service.SubprojectService;
import org.example.eksamenkea.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/task")
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

    @GetMapping("/add-task") //Amalie
    public String addTask(@RequestParam("subprojectId") int subprojectId, HttpSession session, Model model) {
        Task task = new Task();
        Employee employee = (Employee) session.getAttribute("employee");

        model.addAttribute("task", task);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("subprojectId", subprojectId);
        return "task/add-task";
    }

    @PostMapping("/task-added") //Amalie
    public String addedTask(@RequestParam("subprojectId") int subprojectId, @ModelAttribute Task task) throws Errorhandling {
        taskService.createTask(task);
        return "redirect:/task/project-leader-tasks?subprojectId=" + subprojectId;
    }

    @GetMapping("/project-leader-tasks") //AM-ZU
    public String getTaskBySubprojectName(@RequestParam("subprojectId") int subprojectId, Model model) throws Errorhandling {
        Subproject subproject = subprojectService.getSubprojectBySubprojectId(subprojectId);
        List<Task> tasks = taskService.getTaskBySubprojectId(subprojectId);
        List<Employee> employeeList = employeeService.getAllWorkers();

        model.addAttribute("tasks", tasks);
        model.addAttribute("subproject", subproject);
        model.addAttribute("employeeList", employeeList);
        return "task/project-leader-task-overview";
    }

    @GetMapping("/worker-overview") //Malthe
    public String showWorkerOverview(HttpSession session, Model model) throws Errorhandling {
        Employee employee = (Employee) session.getAttribute("employee");
        List<Task> taskList = taskService.getTasklistByEmployeeId(employee.getEmployeeId());
        model.addAttribute("tasklist", taskList);
        List<List<Object>> getWorkloadByEmployeeId = employeeService.getWorkloadByEmployeeId(employee.getEmployeeId());
        model.addAttribute("Workload", getWorkloadByEmployeeId);
        return "task/worker-overview";
    }

    @GetMapping("/task-status") //Amalie
    public String updateTaskStatus(@RequestParam("taskId") int taskId, Model model) throws Errorhandling {
        model.addAttribute("task", taskService.getTaskByTaskId(taskId));
        return "task/task-edit-status";
    }

    @PostMapping("/task-status") //Amalie
    public String updatedTask(@ModelAttribute Task task) throws Errorhandling {
        taskService.updateTask(task);
        return "redirect:/task/worker-overview";
    }

    @PostMapping("/assign-worker") //Malthe
    public String assignEmployeeToTask(@RequestParam("subprojectId") int subprojectId,@RequestParam("taskId") int taskId, @RequestParam("employeeEmail") String employeeEmail, Model model) throws Errorhandling {
        Employee employee = employeeService.getEmployeeByEmail(employeeEmail);
        employee.setEmployeeId(employee.getEmployeeId());
        taskService.assignWorkerToTask(taskId, employee.getEmployeeId());

        return "redirect:/task/project-leader-tasks?subprojectId=" + subprojectId;
    }


}
