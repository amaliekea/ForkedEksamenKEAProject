package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.service.Errorhandling;

import java.util.List;

public interface IEmployeeRepository {
    Employee signIn(String email, String password) throws Errorhandling;

    public List<Employee> getAllWorkers() throws Errorhandling;

    public Employee getEmployeeByEmail(String email) throws Errorhandling;
}
