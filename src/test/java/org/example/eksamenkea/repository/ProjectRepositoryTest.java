package org.example.eksamenkea.repository;

import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.service.Errorhandling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class) //Den gør, at Mockito kan håndtere dine @Mock og @InjectMocks annotationer automatisk uden behov for yderligere opsætning

class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    Project project = new Project(1, "testproject", 2000, "testdescription", 1, 100);


    @Test
    void addProject() throws Errorhandling {
        // Arrange

        // Act
        projectRepository.addProject(project);

        // Assert
        verify(projectRepository).addProject(project);
    }

    @Test
    void getProjectFromProjectId() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void getArchivedProjects() {
    }

    @Test
    void archiveProject() throws Errorhandling {
        int projectId = 1;
        Project testProject1 = projectRepository.getProjectFromProjectId(projectId);

        assertTrue(testProject1.getProjectName().equals("testproject"));

        projectRepository.archiveProject(projectId);

        Project testProject2 = projectRepository.getProjectFromProjectId(projectId);

        assertNull(testProject2);
    }

    @Test
    void getProjectsDTOByEmployeeId() {
    }
}