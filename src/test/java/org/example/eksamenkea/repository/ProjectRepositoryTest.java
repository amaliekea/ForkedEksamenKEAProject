package org.example.eksamenkea.repository;

import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.ProjectEmployeeCostDTO;
import org.example.eksamenkea.service.Errorhandling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/TEST-DDL.sql") //Her loader vi altid vores DDL før der bliver kørt tests forfra.
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/TEST-DML.sql")//Her loader vi altid vores DML før der bliver kørt tests forfra
@ExtendWith(MockitoExtension.class) //Den gør, at Mockito kan håndtere dine @Mock og @InjectMocks annotationer automatisk uden behov for yderligere opsætning

class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;


    //DENNE TEST VIRKER MED MYSQL
    @Test
    void addProject() throws Errorhandling {

        int employeeId = 1;
        // Arrange
        Project testProject = new Project("projectTest",5000.00, "test project", employeeId,5000);
        int numberOfProjects = projectRepository.getProjectsDTOByEmployeeId(employeeId).size();

        // Act

        projectRepository.addProject(testProject);
        int expectedNumberOfProjects = numberOfProjects + 1;
        int actualNumberOfProjects = projectRepository.getProjectsDTOByEmployeeId(employeeId).size();
        // Assert
        assertEquals(expectedNumberOfProjects, actualNumberOfProjects);
    }

    @Test
    void getProjectFromProjectId() throws Errorhandling {
        // Arrange
        int projectId = 1;

        // Act
        Project testprojectFromId = projectRepository.getProjectFromProjectId(projectId);

        // Assert
        // Her ved vi at projektet hedder "Project Alpha" ud fra vores DML.sql, så den vil altid være korrekt.
        assertEquals("Project Alpha", testprojectFromId.getProjectName());
    }

    @Test
    void archiveProject() throws Errorhandling {
        int projectId = 1;
        int invalidProjectId = 9999;
        int employeeId = 1;
        int numberOfProjects = projectRepository.getArchivedProjects(employeeId).size();

        // Act

        projectRepository.archiveProject(projectId);
        int expectedNumberOfProjects = numberOfProjects + 1;
        int actualNumberOfProjects = projectRepository.getArchivedProjects(1).size();
        // Assert
        assertEquals(expectedNumberOfProjects, actualNumberOfProjects);

        Project testProject2 = projectRepository.getProjectFromProjectId(invalidProjectId);

        assertNull(testProject2);
    }
}