package org.example.eksamenkea.repository;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.repository.interfaces.IEmployeeRepository;
import org.example.eksamenkea.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("IEMPLOYEEREPOSITORY")
public class EmployeeRepository implements IEmployeeRepository {

    @Override//Amalie
    public Employee signIn(String email, String password) throws Errorhandling {
        Employee employee = null;
        try (Connection con = ConnectionManager.getConnection()) {
            String SQLUser = "SELECT * FROM employee WHERE email = ? AND password = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQLUser);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int employee_id = resultSet.getInt("employee_id");
                String roleString = resultSet.getString("role");
                Role role = Role.valueOf(roleString); // Konvertering fra String til ENUM
                int employee_rate = resultSet.getInt("employee_rate");
                int max_hours = resultSet.getInt("max_hours");
                employee = new Employee(employee_id, email, password, role, employee_rate, max_hours);
            }
        } catch (SQLException e) {
            throw new Errorhandling("Sign-in SQL error: " + e.getMessage());
        }
        return employee;
    }

    @Override //Malthe
    public List<Employee> getAllWorkers() throws Errorhandling {
        List<Employee> workerList = new java.util.ArrayList<>();
        String query = "SELECT * FROM employee WHERE role = 'Worker'";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preSta = con.prepareStatement(query)) {
            ResultSet resultSet = preSta.executeQuery();
            while (resultSet.next()) {
                workerList.add(new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role").toUpperCase()),
                        resultSet.getInt("employee_rate"),
                        resultSet.getInt("max_hours")
                ));
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get all workers: " + e.getMessage());
        }
        return workerList;
    }

    @Override //Malthe
    public Employee getEmployeeByEmail(String email) throws Errorhandling {
        Employee employee = null;
        String query = "SELECT * FROM employee WHERE email = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preSta = con.prepareStatement(query)) {
            preSta.setString(1, email);
            ResultSet resultSet = preSta.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role").toUpperCase()),
                        resultSet.getInt("employee_rate"),
                        resultSet.getInt("max_hours")
                );
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get worker: " + e.getMessage());
        }
        return employee;
    }

    @Override//Amalie
    public List<List<Object>> getWorkloadByEmployeeId(int employeeId) throws Errorhandling {
        List<List<Object>> workloadList = new ArrayList<>();
        String query = "SELECT * FROM employee_workload_pr_day WHERE employee_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, employeeId);

            ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>();
                    row.add(resultSet.getDate("date_column"));
                    row.add(resultSet.getDouble("total_hours_per_day"));
                    row.add(resultSet.getInt("max_hours_per_employee"));
                    workloadList.add(row);
                }
        } catch (SQLException e) {
            throw new Errorhandling("Error retrieving workload for employee ID " + employeeId + ": " + e.getMessage());
        }
        return workloadList;
    }
}
