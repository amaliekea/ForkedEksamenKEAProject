package org.example.eksamenkea.repository;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.repository.interfaces.ISubprojectRepository;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("ISUBPROJECTREPOSITORY")
public class SubprojectRepository implements ISubprojectRepository {

    @Override //Malthe
    public Subproject getSubprojectBySubprojectId(int subprojectId) throws Errorhandling {
        Subproject subproject = null;
        String query = "SELECT * FROM subproject WHERE subproject_id = ?;";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preStat = con.prepareStatement(query)) {

            preStat.setInt(1, subprojectId);

            try (ResultSet resultSet = preStat.executeQuery()) {
                if (resultSet.next()) {
                    subproject = new Subproject(
                            resultSet.getInt("subproject_id"),
                            resultSet.getString("subproject_name"),
                            resultSet.getString("subproject_description"),
                            resultSet.getInt("project_id")
                    );
                }
            }
            return subproject;
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get subproject by subproject ID: " + e.getMessage());
        }
    }

    @Override //Malthe
    public void updateSubproject(Subproject subproject) throws Errorhandling {
        String query = "UPDATE subproject SET subproject_name = ?, subproject_description = ? , project_id = ? WHERE subproject_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preStat = con.prepareStatement(query)){

            preStat.setString(1, subproject.getSubprojectName());
            preStat.setString(2, subproject.getSubprojectDescription());
            preStat.setInt(3, subproject.getProjectId());
            preStat.setInt(4, subproject.getSubprojectId());
            preStat.executeUpdate();
        } catch (SQLException e) {
            throw new Errorhandling("Failed to update subproject " + e.getMessage());
        }
    }
    @Override //Amalie
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
}