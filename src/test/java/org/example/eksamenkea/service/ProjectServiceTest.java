package org.example.eksamenkea.service;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectCostDTO;
import org.example.eksamenkea.repository.interfaces.IProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ApplicationContext context; // Mock ApplicationContext

    @Mock
    private IProjectRepository iProjectRepository; // Mock IProjectRepository

    @InjectMocks
    private ProjectService projectService; // Inject mocks

    private Project project;
    private ProjectCostDTO projectCostDTO;

    @BeforeEach
    void setup() {

        when(context.getBean("IPROJECTREPOSITORY")).thenReturn(iProjectRepository);

        projectService = new ProjectService(context, "IPROJECTREPOSITORY");

        //projekter til at teste på
        project = new Project(1, "testproject", 200, "testdescription", 1, 200);
        projectCostDTO = new ProjectCostDTO(1, "testproject", 200, "testdescription", 1, 200, 100, 100);
    }

    @Test
    void addProject() throws Exception {
        projectService.addProject(project);
        verify(iProjectRepository).addProject(project); // Verify method call
    }

    @Test
    void getProjectFromProjectId() throws Exception {
        when(iProjectRepository.getProjectFromProjectId(1)).thenReturn(project);
        Project result = projectService.getProjectFromProjectId(1);
        assertNotNull(result);
        assertEquals("testproject", result.getProjectName());
        assertEquals(200, result.getBudget());
    }

    @Test
    void archiveProject() throws Exception {
        projectService.archiveProject(1);
        verify(iProjectRepository).archiveProject(1);
    }

    @Test
    void getArchivedProjects() throws Exception {
        projectService.getArchivedProjects(1);
        verify(iProjectRepository).getArchivedProjects(1);
    }

    @Test
    void updateProject() throws Exception {
        projectService.updateProject(project);
        verify(iProjectRepository).updateProject(project);
    }

    @Test
    void getProjectsDTOByEmployeeId() throws Exception {
        projectService.getProjectsDTOByEmployeeId(1);
        verify(iProjectRepository).getProjectsDTOByEmployeeId(1);
        assertNotNull(projectCostDTO);
        assertEquals("testproject", projectCostDTO.getProjectName());
        assertEquals(200, projectCostDTO.getBudget());
    }
}
