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

                    subproject.setSubproject_id(resultSet.getInt("subproject_id"));
                    subproject.setSubproject_name(resultSet.getString("subproject_name"));
                    subproject.setSubproject_description(resultSet.getString("subproject_description"));
                    subproject.setProject_id(resultSet.getInt("project_id"));

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

