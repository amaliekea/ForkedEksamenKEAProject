//package org.example.eksamenkea.repository;
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
//class TaskRepositoryTest {
//    @Autowired
//    private TaskRepository taskRepository;
//
//
//    @Test
//    void createTask() throws Errorhandling {
//        //ARRANGE
//        LocalDate startdate = LocalDate.of(2021, 12, 12);
//        LocalDate enddate = LocalDate.of(2021, 12, 12);
//        Status status = Status.COMPLETE;
//        int employeeId = 1;
//        Task testTask = new Task("test task", startdate, enddate, status, 1, 1, 1, employeeId);
//        int numberOfTasks = taskRepository.getTasklistByEmployeeId(employeeId).size();
//
//        //ACT
//        taskRepository.createTask(testTask);
//        int expectedNumberOfTasks = numberOfTasks + 1;
//        int actualNumberOfTasks = taskRepository.getTasklistByEmployeeId(employeeId).size();
//
//        //ASSERT
//
//        assertEquals(expectedNumberOfTasks, actualNumberOfTasks);
//    }
//
//    @Test
//    void getTaskByTaskId() throws Errorhandling {
//        //ARRANGE & ACT
//        int taskId = 1;
//        Task testTask = taskRepository.getTaskByTaskId(taskId);
//
//        //ASSERT
//        assertEquals("Task 1", testTask.getTaskName());
//    }
//
//    @Test
//    void assignWorkerToTask() throws Errorhandling {
//        //ARRANGE
//        int taskId = 1;
//        int employeeId = 1;
//        int numberOfTasks = taskRepository.getTasklistByEmployeeId(employeeId).size();
//
//        //ACT
//        taskRepository.assignWorkerToTask(taskId, employeeId);
//        int expectedNumberOfTasks = numberOfTasks + 1;
//        int actualNumberOfTasks = taskRepository.getTasklistByEmployeeId(employeeId).size();
//
//        //ASSERT
//        assertEquals(expectedNumberOfTasks, actualNumberOfTasks);
//    }
//}