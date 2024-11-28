package org.example.eksamenkea.service;

import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByProjectId(int projectId) throws Errorhandling {
        return taskRepository.getTasksByProjectId(projectId);
    }

    public List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling {
        return taskRepository.getTaskBySubprojectId(subprojectId);
    }

    public List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling {
        return taskRepository.getTasklistByEmployeeId(employeeId);
    }

    public int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling {
        return taskRepository.getSubprojectIdBySubprojectName(subprojectName);
    }

    public void createTask(Task task) throws Errorhandling {
        taskRepository.createTask(task);
    }
}
