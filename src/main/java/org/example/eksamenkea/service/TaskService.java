package org.example.eksamenkea.service;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.interfaces.ITaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository iTaskRepository;

    public TaskService(ApplicationContext context, @Value("ITASKREPOSITORY") String impl) {
        this.iTaskRepository = (ITaskRepository) context.getBean(impl);
    }

    public List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling {
        return iTaskRepository.getTaskBySubprojectId(subprojectId);
    }

    public List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling {
        return iTaskRepository.getTasklistByEmployeeId(employeeId);
    }

    public void createTask(Task task) throws Errorhandling {
        iTaskRepository.createTask(task);
    }

    public void updateTask(Task task) throws Errorhandling {
        iTaskRepository.updateTask(task);
    }

    public Task getTaskByTaskId(int taskId) throws Errorhandling {
        return iTaskRepository.getTaskByTaskId(taskId);
    }
    public void assignWorkerToTask(int taskId,int employeeId) throws Errorhandling {
        iTaskRepository.assignWorkerToTask(taskId, employeeId);
    }

}
