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

    public EmployeeService(ApplicationContext context, @Value("IEMPLOYEEREPOSITORY") String impl) {
        this.iEmployeeRepository = (IEmployeeRepository) context.getBean(impl);
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
