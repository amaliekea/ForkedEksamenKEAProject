package org.example.eksamenkea.controller;

import org.example.eksamenkea.model.Project;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;
import org.example.eksamenkea.service.ProjectService;
import org.example.eksamenkea.service.SubprojectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubprojectController.class)
class SubprojectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubprojectService subprojectService;

    @MockBean
    private ProjectService projectService;

    private Subproject mockSubproject;
    private Project mockProject;

    @BeforeEach
        //initalisere mockdata før hver test
    void setUp() {
        // Mock Subproject og Project objekter
        mockSubproject = new Subproject(1, "Mock subproject",
                "Mock Description", 1);
        mockProject = new Project(1, "mock project", 12.0,
                "mock description", 1, 120);
    }

    @Test
    void getSubprojectToEdit() throws Exception {
        //Arrange
        int subprojectId = 1; // Mock subproject id

        //simulere at subprojectservice returnere mocksubproject , når metoden kaldes
        when(subprojectService.getSubprojectBySubprojectId(subprojectId)).thenReturn(mockSubproject);

        // Udfør en GET-anmodning til /edit-subproject med subprojectId som parameter
        mockMvc.perform(get("/subproject/edit-subproject")
                        .param("subprojectId", String.valueOf(subprojectId)))
                .andExpect(status().isOk())//tjekker at status er ok
                .andExpect(view().name("subproject/edit-subproject"))//kontrollere at det rigtige view returneres
                .andExpect(model().attribute("subproject", mockSubproject));//kontrollere at modellen indeholder mocksub


    }

    @Test
    void editSubproject() throws Exception {
        // Arrange
        // Simulerer at metoden updateSubproject ikke gør noget, når den bliver kaldt
        doNothing().when(subprojectService).updateSubproject(mockSubproject);// Mock opførsel for void-metoden

        //act og assert
        mockMvc.perform(post("/subproject/edit-subproject")
                        .flashAttr("subproject", mockSubproject))//// Simulerer, at en @ModelAttribute sendes med requesten
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/project-leader-subproject-overview?projectId="
                        + mockSubproject.getProjectId()));

        //bekræft at updatesubproject service  bliver kaldt mindst 1 gang med mocksubprojekt som argument
        verify(subprojectService, times(1)).updateSubproject(mockSubproject);


    }

    @Test
    void showProjectLeaderSubprojectOverview() throws Exception{
        int projectId = 1; //mock projekt id

        // Simulerer, at projectService og subprojectService returnerer mockdata
        when(projectService.getProjectFromProjectId(projectId)).thenReturn(mockProject);
        when(subprojectService.getSubjectsByProjectId(projectId)).thenReturn(List.of(mockSubproject));

        // Udfør en GET-anmodning til /project-leader-subproject-overview med projectId som parameter
        mockMvc.perform(get("/subproject/project-leader-subproject-overview")
                .param("projectId", String.valueOf(projectId)))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject/project-leader-subproject-overview"))
                .andExpect(model().attribute("project", mockProject))
                .andExpect(model().attribute("subprojects",List.of(mockSubproject)));//kontroller at model indeholder listen

    }
}