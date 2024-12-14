package org.example.eksamenkea.model;

import java.time.LocalDate;

public class Task {
    private int taskId;
    private String taskName;
    private LocalDate startdate;
    private LocalDate enddate;
    private Status status;
    private int subprojectId; // FK
    private int employeeId; // FK
    private int estimatedHours;
    private int actualHours;

    public Task(int taskId, String taskName, LocalDate startdate, LocalDate enddate, Status status,
                int subprojectId, int estimatedHours, int actualHours, int employeeId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.subprojectId = subprojectId;
        this.estimatedHours = estimatedHours;
        this.employeeId = employeeId;
        this.actualHours = actualHours;
    }

    public Task() {

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public int getActualHours() {
        return actualHours;
    }

    public void setActualHours(int actualHours) {
        this.actualHours = actualHours;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", status=" + status +
                ", subprojectId=" + subprojectId +
                ", employeeId=" + employeeId +
                ", estimatedHours=" + estimatedHours +
                ", actualHours=" + actualHours +
                '}';
    }
}
