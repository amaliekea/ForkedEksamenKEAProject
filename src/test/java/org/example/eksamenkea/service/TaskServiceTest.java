package org.example.eksamenkea.service;
import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.model.Status;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.interfaces.ITaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ApplicationContext context; // Mock ApplicationContext

    @Mock
    private ITaskRepository ITaskRepository; // Mock ITaskRepository

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setup() {
        when(context.getBean("ITASKREPOSITORY")).thenReturn(ITaskRepository);

        taskService = new TaskService(context, "ITASKREPOSITORY");

        task = new Task(1, "testtask", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 2),
                Status.NOTSTARTED, 1, 1, 1, 1);
    }


    @Test
    void getTasklistByEmployeeId() throws Errorhandling {
        List<Task> tasks = Arrays.asList(task, new Task(2, "secondTask", LocalDate.of(2024, 11, 5),
                LocalDate.of(2024, 11, 6), Status.INPROGRESS, 124, 20, 15, 102));

        when(ITaskRepository.getTasklistByEmployeeId(101)).thenReturn(tasks);

        List<Task> result = taskService.getTasklistByEmployeeId(101);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("testtask", result.get(0).getTaskName());
        assertEquals("secondTask", result.get(1).getTaskName());
    }

    @Test
    void createTask() throws Errorhandling {
        taskService.createTask(task);

        verify(ITaskRepository).createTask(task);
    }

    @Test
    void updateTask() throws Errorhandling {
        Task updatedTask = new Task(1, "updatedTask", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 2),
                Status.COMPLETE, 123, 40, 40, 101);

        taskService.updateTask(updatedTask);

        verify(ITaskRepository).updateTask(updatedTask);
    }

    @Test
    void getTaskByTaskId() throws Errorhandling {
        when(ITaskRepository.getTaskByTaskId(1)).thenReturn(task);

        Task result = taskService.getTaskByTaskId(1);

        assertNotNull(result);
        assertEquals("testtask", result.getTaskName());
        assertEquals(1, result.getEstimatedHours());
    }

    @Test
    void assignWorkerToTask() throws Errorhandling {
        taskService.assignWorkerToTask(101, 1);
        verify(ITaskRepository).assignWorkerToTask(101, 1);
    }
}
