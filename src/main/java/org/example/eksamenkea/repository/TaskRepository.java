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

@Repository("ITASKREPOSITORY")
public class TaskRepository implements ITaskRepository {

    // CREATE-------------------------------------------------------------------
    @Override
    public void createTask(Task task) throws Errorhandling {
        String sqlAddTask = "INSERT INTO task(task_name, start_date, end_date, status, employee_id, estimated_hours, subproject_id, actual_hours) VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlAddTask)) {

            statement.setString(1, task.getTask_name());
            statement.setDate(2, Date.valueOf(task.getStartdate())); // Konverter LocalDate til java.sql.Date
            statement.setDate(3, Date.valueOf(task.getEnddate()));
            statement.setInt(4, task.getStatus().ordinal()); // Enum-værdi
            statement.setInt(5, task.getEmployee_id());
            statement.setInt(6, task.getEstimated_hours());
            statement.setInt(7, task.getSubproject_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Errorhandling("Failed to add task: " + e.getMessage());
        }
    }


    // READ------------------------------------------------------------------
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
                            resultSet.getInt("employee_id") // Fjern actual_hours
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
                            resultSet.getObject("employee_id") != null ? resultSet.getInt("employee_id") : 0
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch tasks for project ID " + projectId + ": " + e.getMessage());
        }

        return tasks;
    }

    @Override
    public int getTaskIdByTaskName(String taskName) throws Errorhandling {
        String query = "SELECT task_id FROM task WHERE task_name = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, taskName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("task_id");
                } else {
                    throw new Errorhandling("Task not found for name: " + taskName);
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get task ID by task name: " + e.getMessage());
        }
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
                            resultSet.getInt("estimated_hours")
                    ));
                }
                return taskList;
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch tasks for employee ID " + employeeId + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteTaskById(int taskId, int employeeId) throws Errorhandling {
        String deleteEmployeeTaskQuery = "DELETE FROM employee_task WHERE task_id = ?";
        String deleteTaskQuery = "DELETE FROM task WHERE task_id = ? AND subproject_id IN " +
                "(SELECT sp.subproject_id FROM subproject sp JOIN project p ON sp.project_id = p.project_id WHERE p.employee_id = ?)";

        try (Connection connection = ConnectionManager.getConnection()) {
            // Start transaktion
            connection.setAutoCommit(false);

            // Slet fra employee_task
            try (PreparedStatement deleteEmployeeTaskStmt = connection.prepareStatement(deleteEmployeeTaskQuery)) {
                deleteEmployeeTaskStmt.setInt(1, taskId);
                deleteEmployeeTaskStmt.executeUpdate();
            }

            // Slet fra task
            try (PreparedStatement deleteTaskStmt = connection.prepareStatement(deleteTaskQuery)) {
                deleteTaskStmt.setInt(1, taskId);
                deleteTaskStmt.setInt(2, employeeId);
                int rowsAffected = deleteTaskStmt.executeUpdate();

                if (rowsAffected == 0) {
                    connection.rollback();
                    throw new Errorhandling("Task not found or user not authorized.");
                }
            }

            // Commit transaktion
            connection.commit();

        } catch (SQLException e) {
            throw new Errorhandling("Failed to delete task: " + e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) throws Errorhandling {
        String updateSql = "UPDATE task SET status = ? WHERE task_id = ?";
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, task.getStatus().name());
            preparedStatement.setInt(2, task.getTask_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Errorhandling("Failed to update task: " + e.getMessage());
        }
    }

    @Override
    public Task getTaskByName(String taskName) throws Errorhandling {
        String query = "SELECT * FROM task WHERE task_name = ?";
        Task task = null;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, taskName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = new Task(
                                resultSet.getInt("task_id"),
                                resultSet.getString("task_name"),
                                resultSet.getDate("start_date").toLocalDate(),
                                resultSet.getDate("end_date").toLocalDate(),
                                Status.valueOf(resultSet.getString("status").toUpperCase()),
                                resultSet.getInt("subproject_id"),
                                resultSet.getInt("estimated_hours"),
                                resultSet.getInt("employee_id")
                        );
            } else {
                throw new Errorhandling("No task found with name: " + taskName);
            }


        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch task by name: " + e.getMessage());
        }

        return task;
    }

}
