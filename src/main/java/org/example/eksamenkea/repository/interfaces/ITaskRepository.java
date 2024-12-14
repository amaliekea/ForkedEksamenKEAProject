package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.Errorhandling;
import java.util.List;

public interface ITaskRepository {

    void createTask(Task task) throws Errorhandling;

    List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling;

    List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling;

    Task getTaskByTaskId(int taskId) throws Errorhandling;

    void updateTask(Task task) throws Errorhandling;

    void assignWorkerToTask(int taskId, int employeeId) throws Errorhandling;
  

}
