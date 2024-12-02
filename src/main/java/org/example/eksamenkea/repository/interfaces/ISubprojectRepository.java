package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.model.Employee;
import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;

import java.util.Set;

public interface ISubprojectRepository {

    int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling;

    Set<Subproject> getAllSubProjectsByProjectId(int projectId) throws Errorhandling; //måske slettes

    Subproject getSubprojectBySubprojectId(int subprojectId) throws Errorhandling;

    void updateSubproject(Subproject subproject) throws Errorhandling;
    }
