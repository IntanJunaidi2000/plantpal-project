package com.fdmgroup.plantpal_project.service;

import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.repository.PlantRepository;
import com.fdmgroup.plantpal_project.repository.CareLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Plant entity business logic.
 * Handles all CRUD operations and acts as middleware between Controller and Repository.
 */
@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;
    
    @Autowired
    private CareLogRepository careLogRepository;

    public List<Plant> findAllPlants() {
        return plantRepository.findAll();
    }

    public Optional<Plant> findPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public Plant updatePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Transactional
    public void deletePlantById(Long id) {
        // First, delete all care logs associated with this plant
        careLogRepository.deleteByPlantId(id);
        
        // Then delete the plant
        plantRepository.deleteById(id);
    }
}