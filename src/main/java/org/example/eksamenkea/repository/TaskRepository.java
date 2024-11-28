package org.example.eksamenkea.repository;

import org.example.eksamenkea.model.Status;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.interfaces.ITaskRepository;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository implements ITaskRepository {


    public List<Task> getTaskBySubprojectId(int subprojectId) throws Errorhandling {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.task_id, t.task_name, t.start_date, t.end_date, t.status, " +
                "t.subproject_id, t.estimated_hours, t.actual_hours , t.employee_id " +
                "FROM task t " +
                "WHERE t.subproject_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, subprojectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(new Task(
                            resultSet.getInt("task_id"),
                            resultSet.getString("task_name"),
                            resultSet.getDate("start_date") != null ? resultSet.getDate("start_date").toLocalDate() : null,
                            resultSet.getDate("end_date") != null ? resultSet.getDate("end_date").toLocalDate() : null,
                            Status.valueOf(resultSet.getString("status").toUpperCase()),
                            resultSet.getInt("subproject_id"),
                            resultSet.getInt("estimated_hours"),
                            resultSet.getInt("actual_hours"),
                            resultSet.getObject("employee_id") != null ? resultSet.getInt("employee_id") : 0

                            ));
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch tasks for subproject ID " + subprojectId + ": " + e.getMessage());
        }

        return tasks;
    }

    // Hent tasks for et specifikt projekt
    public List<Task> getTasksByProjectId(int projectId) throws Errorhandling {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.task_id, t.task_name, t.start_date, t.end_date, t.estimated_hours,t.status,  t.actual_hours, t.subproject_id, t.employee_id " +
                "FROM task t " +
                "JOIN subproject sp ON sp.subproject_id = t.subproject_id " +
                "LEFT JOIN employee_task et ON t.task_id = et.task_id " +
                "WHERE sp.project_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(new Task(
                            resultSet.getInt("task_id"),
                            resultSet.getString("task_name"),
                            resultSet.getDate("start_date") != null ? resultSet.getDate("start_date").toLocalDate() : null,
                            resultSet.getDate("end_date") != null ? resultSet.getDate("end_date").toLocalDate() : null,
                            Status.valueOf(resultSet.getString("status").toUpperCase()),
                            resultSet.getInt("subproject_id"),
                            resultSet.getInt("estimated_hours"),
                            resultSet.getInt("actual_hours"),
                            resultSet.getObject("employee_id") != null ? resultSet.getInt("employee_id") : 0
                            ));
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch tasks for project ID " + projectId + ": " + e.getMessage());
        }

        return tasks;
    }
    public int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling {
        String query = "SELECT subproject_id FROM subproject WHERE subproject_name = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, subprojectName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("subproject_id");
                } else {
                    throw new Errorhandling("Subproject not found for name: " + subprojectName);
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get subproject ID by subproject name: " + e.getMessage());
        }
    }

    public void deleteTaskByID(int taskId) throws Errorhandling{
        String query ="DELETE FROM task WHERE task_id = ?";


    }


    @Override
    public List<Task> getTasklistByEmployeeId(int employeeId) throws Errorhandling {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT t.task_id, t.task_name, t.start_date, t.end_date, t.estimated_hours, t.status, t.actual_hours, t.subproject_id, t.employee_id FROM employee_task et INNER JOIN task t ON et.task_id = t.task_id WHERE et.employee_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    taskList.add(new Task(
                            resultSet.getInt("task_id"),
                            resultSet.getString("task_name"),
                            resultSet.getDate("start_date") != null ? resultSet.getDate("start_date").toLocalDate() : null,
                            resultSet.getDate("end_date") != null ? resultSet.getDate("end_date").toLocalDate() : null,
                            Status.valueOf(resultSet.getString("status").toUpperCase()),
                            resultSet.getInt("subproject_id"),
                            resultSet.getObject("employee_id") != null ? resultSet.getInt("employee_id") : 0,
                            resultSet.getInt("estimated_hours"),
                            resultSet.getInt("actual_hours")
                    ));
                }
                return taskList;
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch tasks for employee ID " + employeeId + ": " + e.getMessage());
        }
    }
}
