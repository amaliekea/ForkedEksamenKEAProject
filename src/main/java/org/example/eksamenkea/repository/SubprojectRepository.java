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
import java.util.HashSet;
import java.util.Set;

@Repository("ISUBPROJECTREPOSITORY")
public class SubprojectRepository implements ISubprojectRepository {

    @Override
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
    // Lavet af Malthe
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

    @Override
    public void updateSubproject(Subproject subproject) throws Errorhandling {
        String query = "UPDATE subproject SET subproject_name = ?, subproject_description = ? , project_id = ? WHERE subproject_id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement preStat = con.prepareStatement(query)){

            preStat.setString(1, subproject.getSubprojectName());
            preStat.setString(2, subproject.getSubprojectDescription());
            preStat.setInt(3, subproject.getSubprojectId());
            preStat.setInt(4, subproject.getProjectId());

            preStat.executeUpdate();
            System.out.println("Updated subproject");
        } catch (SQLException e) {
            throw new Errorhandling("Failed to update subproject " + e.getMessage());
        }
    }


    @Override
    public Set<Subproject> getAllSubProjectsByProjectId(int projectId) throws Errorhandling {
        Set<Subproject> subprojects = new HashSet<>();
        String query = "SELECT * FROM subproject WHERE project_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subproject subproject = new Subproject();

                    subproject.setSubprojectId(resultSet.getInt("subproject_id"));
                    subproject.setSubprojectName(resultSet.getString("subproject_name"));
                    subproject.setSubprojectDescription(resultSet.getString("subproject_description"));
                    subproject.setProjectId(resultSet.getInt("project_id"));

                    // Tilføj subproject til HashSet
                    subprojects.add(subproject);
                }
            }
        } catch (SQLException e) {
            throw new Errorhandling("Failed to get subproject ID by subproject name: " + e.getMessage());
        }
        return subprojects;
    }
}