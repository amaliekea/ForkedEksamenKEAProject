package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.Errorhandling;

import java.util.List;


public interface ISubprojectRepository {

    Subproject getSubprojectBySubprojectId(int subprojectId) throws Errorhandling;

    void updateSubproject(Subproject subproject) throws Errorhandling;

    List<Subproject> getSubjectsByProjectId(int projectId) throws Errorhandling;

    void addSubproject(Subproject subproject) throws Errorhandling;
}
