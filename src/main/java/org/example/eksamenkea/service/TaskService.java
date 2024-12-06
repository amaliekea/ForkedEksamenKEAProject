package org.example.eksamenkea.service;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.interfaces.ITaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;

    public TaskService(ApplicationContext context, @Value("ITASKREPOSITORY") String impl) {
        this.taskRepository = (ITaskRepository) context.getBean(impl);
    }

    public List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling {
        return taskRepository.getTaskBySubprojectId(subprojectId);
    }

    public List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling {
        return taskRepository.getTasklistByEmployeeId(employeeId);
    }

    public void createTask(Task task) throws Errorhandling {
        taskRepository.createTask(task);
    }

    public void updateTask(Task task) throws Errorhandling {
        taskRepository.updateTask(task);
    }

    public Task getTaskByTaskId(int taskId) throws Errorhandling {
        return taskRepository.getTaskByTaskId(taskId);
    }
    public void assignEmployeeToTask(int taskId,int employeeId) throws Errorhandling {
        taskRepository.assignWorkerToTask(taskId, employeeId);
    }

}
