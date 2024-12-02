package org.example.eksamenkea.service;

import org.example.eksamenkea.repository.interfaces.ISubprojectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class SubprojectService {
    private final ISubprojectRepository subprojectRepository;

    public SubprojectService(ApplicationContext context, @Value("ISUBPROJECTREPOSITORY") String impl) {
        this.subprojectRepository = (ISubprojectRepository) context.getBean(impl);
    }

    public int getSubprojectIdBySubprojectName(String subprojectName) throws Errorhandling {
        return subprojectRepository.getSubprojectIdBySubprojectName(subprojectName);
    }
}
