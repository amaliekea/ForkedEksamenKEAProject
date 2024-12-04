package org.example.eksamenkea.model;

public class Subproject {
    private int subprojectId;
    private String subprojectName;
    private String subprojectDescription;
    private int projectId;

    public Subproject(int subprojectId, String subprojectName, String subprojectDescription, int projectId) {
        this.subprojectId = subprojectId;
        this.subprojectName = subprojectName;
        this.subprojectDescription = subprojectDescription;
        this.projectId = projectId;
    }
    public Subproject() {

    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
