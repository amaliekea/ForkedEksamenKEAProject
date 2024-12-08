package org.example.eksamenkea.controller;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.service.EmployeeService;
import org.example.eksamenkea.service.SubprojectService;
import org.example.eksamenkea.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private SubprojectService subprojectService;

    @MockBean
    private EmployeeService employeeService;

    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        mockEmployee = new Employee(1, "worker@test.com", "password123", Role.WORKER, 40, 37);
    }

    @Test
    void shouldShowAddTaskForm() throws Exception {
        int subprojectId = 1;
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);
        Subproject mockSubproject = new Subproject();
        mockSubproject.setSubprojectName("Test Subproject");

        when(subprojectService.getSubprojectBySubprojectId(subprojectId)).thenReturn(mockSubproject);

        mockMvc.perform(get("/add-task").param("subprojectId", String.valueOf(subprojectId)).sessionAttrs(sessionAttributes))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("employeeId", mockEmployee.getEmployeeId()))
                .andExpect(model().attribute("subprojectId", subprojectId))
                .andExpect(model().attribute("subprojectName", "Test Subproject"));
    }

    @Test
    void shouldAddTaskAndRedirect() throws Exception {
        int subprojectId = 1;
        Task mockTask = new Task();

        mockMvc.perform(post("/task-added")
                        .param("subprojectId", String.valueOf(subprojectId))
                        .flashAttr("task", mockTask))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project-leader-tasks?subprojectId=" + subprojectId));

        Mockito.verify(taskService, Mockito.times(1)).createTask(mockTask);
    }

    @Test
    void shouldShowTasksBySubprojectId() throws Exception {
        int subprojectId = 1;
        Subproject mockSubproject = new Subproject();
        List<Task> mockTasks = Collections.emptyList();
        List<Employee> mockEmployees = Collections.emptyList();

        when(subprojectService.getSubprojectBySubprojectId(subprojectId)).thenReturn(mockSubproject);
        when(taskService.getTaskBySubprojectId(subprojectId)).thenReturn(mockTasks);
        when(employeeService.getAllWorkers()).thenReturn(mockEmployees);


        mockMvc.perform(get("/project-leader-tasks").param("subprojectId", String.valueOf(subprojectId)))
                .andExpect(status().isOk())
                .andExpect(view().name("project-leader-task-overview"))
                .andExpect(model().attribute("tasks", mockTasks))
                .andExpect(model().attribute("subproject", mockSubproject))
                .andExpect(model().attribute("employeeList", mockEmployees));
    }

    @Test
    void shouldShowWorkerOverview() throws Exception {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);
        List<Task> mockTasks = Collections.emptyList();

        when(taskService.getTasklistByEmployeeId(mockEmployee.getEmployeeId())).thenReturn(mockTasks);

        mockMvc.perform(get("/worker-overview").sessionAttrs(sessionAttributes))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-overview"))
                .andExpect(model().attribute("tasklist", mockTasks));
    }

    @Test
    void shouldUpdateTaskStatusAndRedirect() throws Exception {
        int taskId = 1;
        Task mockTask = new Task();
        mockTask.setTaskId(taskId);

        when(taskService.getTaskByTaskId(taskId)).thenReturn(mockTask);

        mockMvc.perform(get("/task-status").param("taskId", String.valueOf(taskId)))
                .andExpect(status().isOk())
                .andExpect(view().name("task-edit-status"))
                .andExpect(model().attribute("task", mockTask));
    }
}
