package com.fdmgroup.plantpal_project.service;

import com.fdmgroup.plantpal_project.entity.CareLog;
import com.fdmgroup.plantpal_project.repository.CareLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for CareLog entity business logic.
 * Handles all CRUD operations and acts as middleware between Controller and Repository.
 */
@Service
public class CareLogService {

    @Autowired
    private CareLogRepository careLogRepository;

    public List<CareLog> findAllCareLogs() {
        return careLogRepository.findAll();
    }

    public Optional<CareLog> findCareLogById(Long id) {
        return careLogRepository.findById(id);
    }

    public CareLog saveCareLog(CareLog careLog) {
        return careLogRepository.save(careLog);
    }

    public CareLog updateCareLog(CareLog careLog) {
        return careLogRepository.save(careLog);
    }

    public void deleteCareLogById(Long id) {
        careLogRepository.deleteById(id);
    }
}