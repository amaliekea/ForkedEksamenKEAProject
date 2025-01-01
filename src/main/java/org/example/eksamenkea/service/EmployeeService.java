package org.example.eksamenkea.service;

import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.repository.interfaces.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService  {
    private final IEmployeeRepository iEmployeeRepository;
    //konstruktør injektion med interfaces implementerer dependency injection
    //dependency inversion principle ved kun at afhænge af interface
    //signle responsibility EmployeeService har én opgave: at definere forretningslogik relateret til medarbejdere.
    public EmployeeService(ApplicationContext context, //ApplicationContext bruges til at håndtere alle Spring-beans
                           @Value("IEMPLOYEEREPOSITORY") String impl) { //injicerer værdien i string impl
        this.iEmployeeRepository = (IEmployeeRepository) context.getBean(impl);//context.getBean(impl) henter en instans af bean "IEMPLOYEEREPOSITORY"
    }

    public Employee signIn(String email, String password) throws Errorhandling{
        return iEmployeeRepository.signIn(email, password);
    }

    public List<Employee> getAllWorkers() throws Errorhandling{
        return iEmployeeRepository.getAllWorkers();
    }

    public Employee getEmployeeByEmail(String email) throws Errorhandling{
        return iEmployeeRepository.getEmployeeByEmail(email);
    }

    public List<List<Object>> getWorkloadByEmployeeId(int employeeId) throws Errorhandling {
        return iEmployeeRepository.getWorkloadByEmployeeId(employeeId);
    }
}
