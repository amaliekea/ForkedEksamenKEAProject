package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;

import java.util.List;

public interface IProjectRepository {

     void addProject(Project project) throws Errorhandling;

     List<Subproject> getSubjectsByProjectId(int projectId) throws Errorhandling;

     List<Project> getProjectsByEmployeeId(int employeeId) throws Errorhandling;

     Project getWorkerProjectFromEmployeeId(int employeeId) throws Errorhandling;

     Project getProjectFromProjectId(int projectId) throws Errorhandling;

     void updateProject(Project project) throws Errorhandling;

     void archiveProject(int projectId) throws Errorhandling;

     List<ProjectEmployeeCostDTO> getArchivedProjects(int employeeId) throws Errorhandling;

     List<ProjectEmployeeCostDTO>getProjectsDTOByEmployeeId(int employeeId) throws Errorhandling;

}

