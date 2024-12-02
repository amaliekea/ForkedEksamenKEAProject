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

            preStat.setString(1, subproject.getSubproject_name());
            preStat.setString(2, subproject.getSubproject_description());
            preStat.setInt(3, subproject.getSubproject_id());
            preStat.setInt(4, subproject.getProject_id());

            preStat.executeUpdate();
            System.out.println("Updated subproject");
        } catch (SQLException e) {
            throw new Errorhandling("Failed to update subproject " + e.getMessage());
        }
    }


}
