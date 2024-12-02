package org.example.eksamenkea.repository;

import org.apache.tomcat.jni.Pool;
import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.model.Task;
import org.example.eksamenkea.repository.interfaces.IProjectRepository;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("IPROJECTREPOSITORY")
public class ProjectRepository implements IProjectRepository {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectRepository(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // CREATE------------------------------------------------------------------------------
    @Override
    public void addProject(Project project) throws Errorhandling {
        String sqlAddProject = "INSERT INTO project(project_name, budget, project_description, employee_id, material_cost, employee_cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlAddProject)) {

            statement.setString(1, project.getProject_name());
            statement.setDouble(2, project.getBudget());
            statement.setString(3, project.getProject_description());
            statement.setInt(4, project.getEmployee_id());
            statement.setDouble(5, project.getMaterial_cost());
            statement.setDouble(6, project.getEmployee_cost());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Errorhandling("Failed to add project: " + e.getMessage());
        }
    }

    // READ--------------------------------------------------------------------------------
    @Override
    public List<Project> getProjectsByEmployeeId(int employeeId) throws Errorhandling {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project WHERE employee_id = ? AND is_archived = FALSE";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projects.add(new Project(
                            resultSet.getInt("project_id"),
                            resultSet.getString("project_name"),
                            resultSet.getDouble("budget"),
                            resultSet.getString("project_description"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("material_cost")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get projects by employee ID: " + e.getMessage());
        }
        return projects;
    }

    public int getProjectIdByProjectName(String projectName) throws Errorhandling {
        String query = "SELECT project_id FROM project WHERE project_name = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, projectName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("project_id");
                } else {
                    throw new Errorhandling("Project not found for name: " + projectName);
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get project ID by project name: " + e.getMessage());
        }
    }


    @Override
    public List<Subproject> getSubjectsByProjectId(int projectId) throws Errorhandling {
        List<Subproject> subprojects = new ArrayList<>();
        String query = "SELECT * FROM subproject WHERE project_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    subprojects.add(new Subproject(
                            resultSet.getInt("subproject_id"),
                            resultSet.getString("subproject_name"),
                            resultSet.getString("subproject_description"),
                            resultSet.getInt("project_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get subprojects by project ID: " + e.getMessage());
        }
        return subprojects;
    }


    @Override
    public Project getWorkerProjectFromEmployeeId(int employeeId) throws Errorhandling {
        Project project = null;
        String query = "SELECT * FROM project WHERE employee_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    project = new Project(
                            resultSet.getInt("project_id"),
                            resultSet.getString("project_name"),
                            resultSet.getDouble("budget"),
                            resultSet.getString("project_description"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("material_cost")
                    );
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to fetch project for employee ID " + employeeId + ": " + e.getMessage());
        }
        return project;
    }

    @Override
    public List<Project> getArchivedProjects() throws Errorhandling {
        List<Project> archivedProjects = new ArrayList<>();
        String query = "SELECT * FROM project WHERE is_archived = TRUE";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                archivedProjects.add(new Project(
                        resultSet.getInt("project_id"),
                        resultSet.getString("project_name"),
                        resultSet.getDouble("budget"),
                        resultSet.getString("project_description"),
                        resultSet.getInt("employee_id"),
                        resultSet.getInt("material_cost"),
                        resultSet.getInt("employee_cost")
                ));
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get any archievd projects: " + e.getMessage());
        }
        return archivedProjects;


    }

    //DELETE-----------------------------------------------------------------------
    @Override
    public void archiveProject(int projectId) throws Errorhandling {
        String archiveProjectQuery = "UPDATE project SET is_archived = TRUE WHERE project_id = ?";
        String archiveSubprojectQuery = "UPDATE subproject SET is_archived = TRUE WHERE project_id = ?";
        String archiveTaskQuery = "UPDATE task t " +
                "JOIN subproject sp ON t.subproject_id = sp.subproject_id " +
                "SET t.is_archived = TRUE " +
                "WHERE sp.project_id = ?";

        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false); // Start transaktion

            try (PreparedStatement projectStmt = connection.prepareStatement(archiveProjectQuery);
                 PreparedStatement subprojectStmt = connection.prepareStatement(archiveSubprojectQuery);
                 PreparedStatement taskStmt = connection.prepareStatement(archiveTaskQuery)) {

                // Arkiver projekt
                projectStmt.setInt(1, projectId);
                int projectRows = projectStmt.executeUpdate();
                if (projectRows == 0) {
                    throw new Errorhandling("No project found with ID: " + projectId);
                }

                // Arkiver tilknyttede subprojekter
                subprojectStmt.setInt(1, projectId);
                subprojectStmt.executeUpdate();

                // Arkiver tilknyttede tasks
                taskStmt.setInt(1, projectId);
                taskStmt.executeUpdate();

                connection.commit(); // Udfør transaktionen
            } catch (SQLException e) {
                connection.rollback(); // Rul tilbage ved fejl
                throw new Errorhandling("Failed to archive project and related data: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new Errorhandling("Database error during archiving: " + e.getMessage());
        }
    }



    @Override
    public int calculateEmployeeCost(Project project) throws Errorhandling {
        //rate * actualhours
        int employeeCost = 0;
        List<Task> allProjectTasks = taskRepository.getTasksByProjectId(project.getProject_id());
        Set<Employee> allEmployeeForProject = getAllEmployeeForProject(project.getProject_id());
        HashSet<Task> employeeTaskSet = new HashSet<>(allProjectTasks); // Konvertering af listen til et HashSet da vi ikke ønsker duplicates
        for (Task task : allProjectTasks) {
            for (Employee employee : allEmployeeForProject) {
                if (task.getEmployee_id() == employee.getEmployee_id()) {
                    employeeCost += employee.getEmployee_rate()+task.getActual_hours();
                }
            }
        }
        return employeeCost;
    }

    @Override
    public Set<Employee> getAllEmployeeForProject(int projectId) throws Errorhandling {
        Set<Employee> employeeInProject = new HashSet<>();
        List<Task> projectTasks = taskRepository.getTasksByProjectId(projectId);
        List<Employee> getAllWorker = employeeRepository.getAllWorkers();

        for (Employee employee : getAllWorker) {
            for (Task task : projectTasks) {
                if (employee.getEmployee_id() == task.getEmployee_id()) {
                    employeeInProject.add(employee);
                }
            }
        }
        return employeeInProject;
    }
}
