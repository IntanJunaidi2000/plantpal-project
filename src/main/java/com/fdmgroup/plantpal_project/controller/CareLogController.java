package com.fdmgroup.plantpal_project.controller;

import com.fdmgroup.plantpal_project.entity.CareLog;
import com.fdmgroup.plantpal_project.service.CareLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling all CareLog-related HTTP requests.
 * Provides CRUD operations for the CareLog entity.
 */
@RestController
@RequestMapping("/api/carelogs")
public class CareLogController {

    @Autowired
    private CareLogService careLogService;
    
    
    /**
     * Retrieves all CareLogs
     * @return List of all CareLogs with 200 OK status
     */
    @GetMapping
    public List<CareLog> getAllCareLogs() {
        return careLogService.findAllCareLogs();
    }

    /**
     * Retrieves a specific CareLog by ID
     * @return CareLog entity with 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CareLog> getCareLogById(@PathVariable Long id) {
        Optional<CareLog> careLog = careLogService.findCareLogById(id);
        return careLog.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new CareLog
     * @return Created CareLog entity with 201 CREATED status
     */
    @PostMapping
    public CareLog createCareLog(@RequestBody CareLog careLog) {
        return careLogService.saveCareLog(careLog);
    }
    
    /**
     * Updates an existing CareLog
     * @return Updated CareLog entity with 200 OK if found, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<CareLog> updateCareLog(@PathVariable Long id, @RequestBody CareLog careLogDetails) {
        Optional<CareLog> careLog = careLogService.findCareLogById(id);
        if (careLog.isPresent()) {
            CareLog existingCareLog = careLog.get();
            existingCareLog.setCareType(careLogDetails.getCareType());
            existingCareLog.setCareDate(careLogDetails.getCareDate());
            existingCareLog.setNotes(careLogDetails.getNotes());
            return ResponseEntity.ok(careLogService.updateCareLog(existingCareLog));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a CareLog by ID
     * @return 200 OK if deleted, 404 NOT FOUND if CareLog doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareLog(@PathVariable Long id) {
        Optional<CareLog> careLog = careLogService.findCareLogById(id);
        if (careLog.isPresent()) {
            careLogService.deleteCareLogById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}