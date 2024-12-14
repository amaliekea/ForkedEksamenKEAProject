package org.example.eksamenkea.controller;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        mockEmployee = new Employee(1, "leader@test.com", "password123", Role.PROJECTLEADER, 123, 37);
    }

    @Test
    void ShowProjectLeaderOverview() throws Exception {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);
        when(projectService.getProjectsDTOByEmployeeId(mockEmployee.getEmployeeId()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/project/project-leader-overview").sessionAttrs(sessionAttributes))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-leader-overview"))
                .andExpect(model().attribute("projects", Collections.emptyList()));
    }

    @Test
    void ShowAddProjectForm() throws Exception {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);

        mockMvc.perform(get("/project/add-project").sessionAttrs(sessionAttributes))
                .andExpect(status().isOk())
                .andExpect(view().name("project/add-project-form"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("employeeId", mockEmployee.getEmployeeId()));
    }

    @Test
    void AddProjectAndRedirect() throws Exception {
        Project mockProject = new Project();

        mockMvc.perform(post("/project/project-added").flashAttr("project", mockProject))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/project-leader-overview"));

        Mockito.verify(projectService, Mockito.times(1)).addProject(mockProject);
    }

    @Test
    void ShowEditProjectFormTest() throws Exception {
        int projectId = 1;
        Project mockProject = new Project();
        when(projectService.getProjectFromProjectId(projectId)).thenReturn(mockProject);

        mockMvc.perform(get("/project/" + projectId + "/edit-project"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/edit-project"))
                .andExpect(model().attribute("project", mockProject));
    }

    @Test
    void shouldEditProjectAndRedirect() throws Exception {
        Project mockProject = new Project();
        mockProject.setProjectName("New Project Name");

        mockMvc.perform(post("/project/edit-project").flashAttr("project", mockProject))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/project-leader-overview"));

        Mockito.verify(projectService, Mockito.times(1)).updateProject(mockProject);
    }

    @Test
    void shouldShowArchivedProjects() throws Exception {
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);
        when(projectService.getArchivedProjects(mockEmployee.getEmployeeId()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/project/archived-project-overview").sessionAttrs(sessionAttributes))
                .andExpect(status().isOk())
                .andExpect(view().name("project/archived-project-overview"))
                .andExpect(model().attribute("archivedProjects", Collections.emptyList()));
    }

    @Test
    void shouldArchiveProjectAndRedirect() throws Exception {
        int projectId = 1;
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);

        mockMvc.perform(post("/project/archive-project").param("projectId", String.valueOf(projectId)).sessionAttrs(sessionAttributes))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/project-leader-overview"));

        Mockito.verify(projectService, Mockito.times(1)).archiveProject(projectId);
    }
}
