package org.example.eksamenkea.repository.interfaces;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IProjectRepository {

     void addProject(Project project) throws Errorhandling;

     Project getProjectFromProjectId(int projectId) throws Errorhandling;

     void updateProject(Project project) throws Errorhandling;

     void archiveProject(int projectId) throws Errorhandling;

     List<ProjectEmployeeCostDTO> getArchivedProjects(int employeeId) throws Errorhandling;

     List<ProjectEmployeeCostDTO>getProjectsDTOByEmployeeId(int employeeId) throws Errorhandling;

     int calculateTimeConsumptionProject(int subprojectId) throws Errorhandling;
}

