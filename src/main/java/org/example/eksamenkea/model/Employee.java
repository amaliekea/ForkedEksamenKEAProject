package org.example.eksamenkea.model;

public class Employee {
    private int employeeId;
    private String email;
    private String password;
    private Role role; // Enum
    private int employeeRate;
    private int maxHours;

    public Employee(int employeeId, String email, String password, Role role, int employeeRate, int maxHours) {
        this.employeeId = employeeId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.employeeRate = employeeRate;
        this.maxHours = maxHours;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getEmployeeRate() {
        return employeeRate;
    }

    public void setEmployeeRate(int employeeRate) {
        this.employeeRate = employeeRate;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }
}
