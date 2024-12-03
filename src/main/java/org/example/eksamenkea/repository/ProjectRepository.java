package org.example.eksamenkea.repository;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.repository.interfaces.IProjectRepository;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sqlAddProject = "INSERT INTO project(project_name, budget, project_description, employee_id, material_cost,is_archived) VALUES (?, ?, ?, ?, ?,?)";
        System.out.println(project);
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlAddProject)) {

            statement.setString(1, project.getProject_name());
            statement.setDouble(2, project.getBudget());
            statement.setString(3, project.getProject_description());
            statement.setInt(4, project.getEmployee_id());
            statement.setDouble(5, project.getMaterial_cost());
            statement.setBoolean(6, false);
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
    public Project getProjectFromProjectId(int projectId) throws Errorhandling {
        Project project = null;
        String query = "SELECT * FROM project WHERE project_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);

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
            throw new Errorhandling("Failed to fetch project for project ID " + projectId + ": " + e.getMessage());
        }
        return project;
    }

    @Override
    public void updateProject(Project project) throws Errorhandling {
        String sqlAddProject = "UPDATE project SET project_name = ?, budget = ?, project_description = ?, employee_id = ?, material_cost = ? WHERE project_id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(sqlAddProject)) {

            statement.setString(1, project.getProject_name());
            statement.setDouble(2, project.getBudget());
            statement.setString(3, project.getProject_description());
            statement.setInt(4, project.getEmployee_id());
            statement.setDouble(5, project.getMaterial_cost());
            statement.setInt(6, project.getProject_id());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new Errorhandling("Failed to update project: " + e.getMessage());
        }
    }


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
                        resultSet.getInt("material_cost")
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
    public List<ProjectEmployeeCostDTO> getProjectsDTOByEmployeeId(int employeeId) throws Errorhandling {
        List<ProjectEmployeeCostDTO> projects = new ArrayList<>();
        String queryView = "SELECT project.*,SUM(task.actual_hours * employee.employee_rate) AS employee_cost FROM project " +
                "LEFT JOIN subproject ON project.project_id = subproject.project_id " +
                "LEFT JOIN task ON subproject.subproject_id = task.subproject_id " +
                "LEFT JOIN employee ON task.employee_id = employee.employee_id " +
                "WHERE project.employee_id = ? AND project.is_archived = FALSE Group by project.project_id";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(queryView)) {
            preparedStatement1.setInt(1, employeeId);

            ResultSet resultSet = preparedStatement1.executeQuery();
                while (resultSet.next()) {
                    projects.add(new ProjectEmployeeCostDTO(
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
            throw new Errorhandling("Failed to get projects by employee ID: " + e.getMessage());
        }
        return projects;
    }
}
