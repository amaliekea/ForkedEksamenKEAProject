package org.example.eksamenkea.repository.interfaces;

import org.example.eksamenkea.service.Errorhandling;

public interface ISubprojectRepository {

    int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling;

}
