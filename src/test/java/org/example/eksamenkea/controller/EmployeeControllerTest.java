package org.example.eksamenkea.controller;

import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Role;
import org.example.eksamenkea.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService; // Mocket service-layer afhængighed

    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        // Initialiserer mock-objektet
        mockEmployee = new Employee(1, "zuzu@hotmail.com", "zuzu123", Role.PROJECTLEADER, 123, 37);
    }

    @Test
    void shouldReturnLoginView() throws Exception {
        // Tester at GET /login returnerer login-siden
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk()) // Forvent status 200
                .andExpect(view().name("login")); // Forvent visning af login-siden
    }

    @Test
    void shouldDisplayHomepageWhenLoggedIn() throws Exception {
        // Simulerer en aktiv session
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);

        mockMvc.perform(get("/").sessionAttrs(sessionAttributes)) // GET / med session
                .andExpect(status().isOk()) // Forvent status 200
                .andExpect(view().name("homepage")) // Forvent visning af homepage
                .andExpect(model().attribute("employeeAvaliable", true)); // Kontroller, at modellen indeholder korrekt data
    }

    @Test
    void shouldRedirectToLoggedInWhenCorrect() throws Exception {
        // Arrange
        String email = "zuzu@hotmail.com";
        String password = "zuzu123";

        when(employeeService.signIn(email, password)).thenReturn(mockEmployee); // Mock signIn-kaldet

        // Act + Assert
        mockMvc.perform(post("/validate_login")
                        .param("email", email)
                        .param("password", password)) // POST til /validate_login
                .andExpect(status().is3xxRedirection()) // Forventer redirect status
                .andExpect(redirectedUrl("/logged_in")) // Redirect til logged_in
                .andExpect(request().sessionAttribute("employee", mockEmployee)) // Kontroller session-attributter
                .andExpect(request().sessionAttribute("userRole", mockEmployee.getRole()))
                .andExpect(request().sessionAttribute("employeeId", mockEmployee.getEmployeeId()));

        Mockito.verify(employeeService, Mockito.times(1)).signIn(email, password); // Bekræft at signIn blev kaldt præcist 1 gang
    }

    @Test
    void shouldRedirectToProjectLeaderOverviewWhenLoggedInAsProjectLeader() throws Exception {
        // Simuler en session med en ProjectLeder
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);

        mockMvc.perform(get("/logged_in").sessionAttrs(sessionAttributes)) // GET /logged_in
                .andExpect(status().is3xxRedirection()) // Forvent redirect status
                .andExpect(redirectedUrl("/project-leader-overview")); // Redirect til Project Leader-overview
    }

    @Test
    void shouldInvalidateSessionOnLogout() throws Exception {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("employee", mockEmployee);

        // Act + Assert
        mockMvc.perform(get("/logout").sessionAttrs(sessionAttributes)) // GET /logout
                .andExpect(status().is3xxRedirection()) // Forventer redirect
                .andExpect(redirectedUrl("/")) // Redirecter til forside
                .andExpect(request().sessionAttributeDoesNotExist("employee")) // Kontroller at sessionen er tømt
                .andExpect(request().sessionAttributeDoesNotExist("userRole"))
                .andExpect(request().sessionAttributeDoesNotExist("employeeId"));
    }
}

