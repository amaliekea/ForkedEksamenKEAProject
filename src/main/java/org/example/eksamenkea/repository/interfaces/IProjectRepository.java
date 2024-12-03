package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;

import java.util.List;

public interface IProjectRepository {

    void addProject(Project project) throws Errorhandling;

    List<Subproject> getSubjectsByProjectId(int projectId) throws Errorhandling;

    List<Project> getProjectsByEmployeeId(int employeeId) throws Errorhandling;

    public Project getWorkerProjectFromEmployeeId(int employeeId) throws Errorhandling;

    int getProjectIdByProjectName(String projectName) throws Errorhandling;


    Project getProjectFromProjectId(int projectId) throws Errorhandling;

    public void updateProject(Project project) throws Errorhandling;


    void archiveProject(int projectId) throws Errorhandling;

    List<Project> getArchivedProjects() throws Errorhandling;


    public double calculateEmployeeCost(int projectId) throws Errorhandling;

    public List<ProjectEmployeeCostDTO>getProjectsDTOByEmployeeId(int employeeId) throws Errorhandling;

}

