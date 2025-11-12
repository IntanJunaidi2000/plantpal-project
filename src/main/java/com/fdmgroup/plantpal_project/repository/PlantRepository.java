package com.fdmgroup.plantpal_project.repository;

import com.fdmgroup.plantpal_project.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    // Find plants by user ID
    List<Plant> findByUserId(Long userId);
    
    // Delete plants by user ID using native query
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM plants WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
    
    // Get plant IDs by user ID
    @Query(value = "SELECT id FROM plants WHERE user_id = :userId", nativeQuery = true)
    List<Long> findPlantIdsByUserId(@Param("userId") Long userId);
}