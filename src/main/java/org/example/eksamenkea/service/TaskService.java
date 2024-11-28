package org.example.eksamenkea.service;

import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.TaskRepository;
import org.example.eksamenkea.repository.interfaces.IProjectRepository;
import org.example.eksamenkea.repository.interfaces.ITaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;

    public TaskService(ApplicationContext context, @Value("ITASKREPOSITORY") String impl) {
        this.taskRepository = (ITaskRepository) context.getBean(impl);
    }


    public List<Task> getTasksByProjectId(int projectId) throws Errorhandling {
        return taskRepository.getTasksByProjectId(projectId);
    }

    public List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling{
        return taskRepository.getTaskBySubprojectId(subprojectId);
    }

    public List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling {
        return taskRepository.getTasklistByEmployeeId(employeeId);
    }

    public int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling{
        return taskRepository.getSubprojectIdBySubprojectName(subprojectName);
    }

    public void assignEmployeeToTask(int taskId,int employeeId) throws Errorhandling {
        taskRepository.assignWorkerIdToTask(taskId, employeeId);
    };




}
