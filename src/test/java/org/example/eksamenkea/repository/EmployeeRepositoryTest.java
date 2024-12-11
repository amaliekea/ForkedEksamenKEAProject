//package org.example.eksamenkea.repository;
//import org.example.eksamenkea.model.Employee;
//import org.example.eksamenkea.model.Role;
//import org.example.eksamenkea.model.Status;
//import org.example.eksamenkea.model.Task;
//import org.example.eksamenkea.service.Errorhandling;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/TEST-DDL.sql") //Her loader vi altid vores DDL før der bliver kørt tests forfra.
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/TEST-DML.sql")//Her loader vi altid vores DML før der bliver kørt tests forfra
//
//class EmployeeRepositoryTest {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//
//    @Test
//    void getEmployeeByEmail() throws Errorhandling {
//        //ARRANGE
//        String email = "worker2@example.com";
//
//        //ACT
//        employeeRepository.signIn(email, "password");
//        Employee employee = employeeRepository.getEmployeeByEmail(email);
//
//        //ASSERT
//        assertEquals(email, employee.getEmail());
//
//    }
//
//    @Test
//    void getAllWorkers() throws Errorhandling {
//        //ARRANGE
//        int expected = 2;
//
//        //ACT
//        int actual = employeeRepository.getAllWorkers().size();
//
//        //ASSERT
//        assertEquals(expected, actual);
//    }
//}