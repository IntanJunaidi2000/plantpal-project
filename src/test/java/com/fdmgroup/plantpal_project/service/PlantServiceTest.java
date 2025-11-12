package com.fdmgroup.plantpal_project.service;

import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PlantService using Mockito to mock the repository layer
 */
@ExtendWith(MockitoExtension.class)
class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @InjectMocks
    private PlantService plantService;

    @Test
    void testFindAllPlants() {
        // Arrange - set up test data
        Plant plant1 = new Plant();
        plant1.setId(1L);
        plant1.setNickname("Sunny");
        
        Plant plant2 = new Plant();
        plant2.setId(2L);
        plant2.setNickname("Spike");
        
        List<Plant> expectedPlants = Arrays.asList(plant1, plant2);
        
        // Mock the repository behavior
        when(plantRepository.findAll()).thenReturn(expectedPlants);

        // Act - call the method being tested
        List<Plant> actualPlants = plantService.findAllPlants();

        // Assert - verify the results
        assertEquals(2, actualPlants.size());
        assertEquals("Sunny", actualPlants.get(0).getNickname());
        verify(plantRepository, times(1)).findAll();
    }

    @Test
    void testFindPlantById_WhenPlantExists() {
        // Arrange
        Long plantId = 1L;
        Plant expectedPlant = new Plant();
        expectedPlant.setId(plantId);
        expectedPlant.setNickname("Sunny");
        
        when(plantRepository.findById(plantId)).thenReturn(Optional.of(expectedPlant));

        // Act
        Optional<Plant> actualPlant = plantService.findPlantById(plantId);

        // Assert
        assertTrue(actualPlant.isPresent());
        assertEquals("Sunny", actualPlant.get().getNickname());
        verify(plantRepository, times(1)).findById(plantId);
    }

    @Test
    void testFindPlantById_WhenPlantNotExists() {
        // Arrange
        Long plantId = 999L;
        when(plantRepository.findById(plantId)).thenReturn(Optional.empty());

        // Act
        Optional<Plant> actualPlant = plantService.findPlantById(plantId);

        // Assert
        assertFalse(actualPlant.isPresent());
        verify(plantRepository, times(1)).findById(plantId);
    }

    @Test
    void testSavePlant() {
        // Arrange
        Plant plantToSave = new Plant();
        plantToSave.setNickname("New Plant");
        
        Plant savedPlant = new Plant();
        savedPlant.setId(1L);
        savedPlant.setNickname("New Plant");
        
        when(plantRepository.save(plantToSave)).thenReturn(savedPlant);

        // Act
        Plant result = plantService.savePlant(plantToSave);

        // Assert
        assertNotNull(result.getId());
        assertEquals("New Plant", result.getNickname());
        verify(plantRepository, times(1)).save(plantToSave);
    }

    @Test
    void testUpdatePlant() {
        // Arrange
        Plant plantToUpdate = new Plant();
        plantToUpdate.setId(1L);
        plantToUpdate.setNickname("Updated Plant");
        
        when(plantRepository.save(plantToUpdate)).thenReturn(plantToUpdate);

        // Act
        Plant result = plantService.updatePlant(plantToUpdate);

        // Assert
        assertEquals("Updated Plant", result.getNickname());
        verify(plantRepository, times(1)).save(plantToUpdate);
    }

    @Test
    void testDeletePlantById() {
        // Arrange
        Long plantId = 1L;
        doNothing().when(plantRepository).deleteById(plantId);

        // Act
        plantService.deletePlantById(plantId);

        // Assert
        verify(plantRepository, times(1)).deleteById(plantId);
    }
}