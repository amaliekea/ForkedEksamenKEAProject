package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.service.Errorhandling;

import java.sql.SQLException;
import java.util.List;

public interface ITaskRepository {

    List<Task> getTasksByProjectId(int projectId) throws Errorhandling;

    List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling;

    List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling;


    void createTask(Task task) throws Errorhandling;

    void deleteTaskById(int taskId, int employeeId) throws Errorhandling;

    void updateTask(Task task) throws Errorhandling;

    int getTaskIdByTaskName(String taskName) throws Errorhandling;
}
