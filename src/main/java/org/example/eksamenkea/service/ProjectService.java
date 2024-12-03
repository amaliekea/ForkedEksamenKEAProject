package org.example.eksamenkea.service;

import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.repository.interfaces.IProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final IProjectRepository projectRepository;

    public ProjectService(ApplicationContext context, @Value("IPROJECTREPOSITORY") String impl) {
        this.projectRepository = (IProjectRepository) context.getBean(impl);
    }


    public List<Project> getProjectsByEmployeeId(int employeeId) throws Errorhandling {
        return projectRepository.getProjectsByEmployeeId(employeeId);

    }

    public Project getWorkerProjectFromEmployeeId(int employeeId) throws Errorhandling {
        return projectRepository.getWorkerProjectFromEmployeeId(employeeId);
    }


    public int getProjectIdByProjectName(String projectName) throws Errorhandling {
        return projectRepository.getProjectIdByProjectName(projectName);
    }


    public List<Subproject> getSubjectsByProjectId(int projectId) throws Errorhandling {
        return projectRepository.getSubjectsByProjectId(projectId);
    }

    public void addProject(Project project) throws Errorhandling {
        projectRepository.addProject(project);
    }

    public void archiveProject(int projectId) throws Errorhandling {
        projectRepository.archiveProject(projectId);
    }

    public List<Project> getArchivedProjects() throws Errorhandling {
        return projectRepository.getArchivedProjects();
    }

    public Project getProjectFromProjectId(int projectId) throws Errorhandling {
        return projectRepository.getProjectFromProjectId(projectId);
    }

    public void updateProject(Project project) throws Errorhandling {
        projectRepository.updateProject(project);
    }

    public List<ProjectEmployeeCostDTO> getProjectsDTOByEmployeeId(int employeeId) throws Errorhandling {
        return projectRepository.getProjectsDTOByEmployeeId(employeeId);
    }

}
