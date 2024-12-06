//package org.example.eksamenkea.repository;
//
//import org.example.eksamenkea.model.Project;
//import org.example.eksamenkea.service.Errorhandling;
//import org.example.eksamenkea.service.ProjectService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class) //Den gør, at Mockito kan håndtere dine @Mock og @InjectMocks annotationer automatisk uden behov for yderligere opsætning
//class ProjectRepositoryTest {
//    @Mock
//    private ProjectRepository projectRepository;
//    @InjectMocks
//    private ProjectService projectService;
//
//
//    @Test
//    void addProject() throws Errorhandling {
//        // Arrange
//        Project project = new Project(1, "testproject", 2000, "testdescription", 1, 100);
//
//        // Act
//        projectService.addProject(project);
//
//        // Assert
//        verify(projectRepository).addProject(project);
//    }
//
//    @Test
//    void getProjectFromProjectId() {
//    }
//
//    @Test
//    void updateProject() {
//    }
//
//    @Test
//    void getArchivedProjects() {
//    }
//
//    @Test
//    void archiveProject() {
//    }
//
//    @Test
//    void getProjectsDTOByEmployeeId() {
//    }
//}