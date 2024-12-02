package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.model.Subproject;
import org.example.eksamenkea.service.Errorhandling;

public interface ISubprojectRepository {

    int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling;

    Subproject getSubprojectBySubprojectId(int subprojectId) throws Errorhandling;

    void updateSubproject(Subproject subproject) throws Errorhandling;
    }
