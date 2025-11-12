package com.fdmgroup.plantpal_project.repository;

import com.fdmgroup.plantpal_project.entity.CareLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {
    
    // Delete care logs by plant ID using native query
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM care_logs WHERE plant_id = :plantId", nativeQuery = true)
    void deleteByPlantId(@Param("plantId") Long plantId);
    
    // Delete all care logs for multiple plant IDs
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM care_logs WHERE plant_id IN :plantIds", nativeQuery = true)
    void deleteByPlantIds(@Param("plantIds") List<Long> plantIds);
}