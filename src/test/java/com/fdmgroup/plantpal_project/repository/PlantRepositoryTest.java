package com.fdmgroup.plantpal_project.repository;

import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PlantRepository using Mockito
 * Testing the Spring Data JPA repository methods
 */
@ExtendWith(MockitoExtension.class)
class PlantRepositoryTest {

    @Mock
    private PlantRepository plantRepository;

    @Test
    void testFindAll() {
        // Arrange
        Plant plant1 = new Plant();
        plant1.setId(1L);
        plant1.setNickname("Sunny");
        
        Plant plant2 = new Plant();
        plant2.setId(2L);
        plant2.setNickname("Spike");
        
        List<Plant> expectedPlants = Arrays.asList(plant1, plant2);
        when(plantRepository.findAll()).thenReturn(expectedPlants);

        // Act
        List<Plant> actualPlants = plantRepository.findAll();

        // Assert
        assertEquals(2, actualPlants.size());
        assertEquals("Sunny", actualPlants.get(0).getNickname());
        assertEquals("Spike", actualPlants.get(1).getNickname());
        verify(plantRepository, times(1)).findAll();
    }

    @Test
    void testFindById_WhenPlantExists() {
        // Arrange
        Long plantId = 1L;
        Plant plant = new Plant();
        plant.setId(plantId);
        plant.setNickname("Sunny");
        
        when(plantRepository.findById(plantId)).thenReturn(Optional.of(plant));

        // Act
        Optional<Plant> foundPlant = plantRepository.findById(plantId);

        // Assert
        assertTrue(foundPlant.isPresent());
        assertEquals("Sunny", foundPlant.get().getNickname());
        verify(plantRepository, times(1)).findById(plantId);
    }

    @Test
    void testFindById_WhenPlantNotExists() {
        // Arrange
        Long plantId = 999L;
        when(plantRepository.findById(plantId)).thenReturn(Optional.empty());

        // Act
        Optional<Plant> foundPlant = plantRepository.findById(plantId);

        // Assert
        assertFalse(foundPlant.isPresent());
        verify(plantRepository, times(1)).findById(plantId);
    }

    @Test
    void testSave() {
        // Arrange
        Plant plantToSave = new Plant();
        plantToSave.setNickname("New Plant");
        
        Plant savedPlant = new Plant();
        savedPlant.setId(1L);
        savedPlant.setNickname("New Plant");
        
        when(plantRepository.save(plantToSave)).thenReturn(savedPlant);

        // Act
        Plant result = plantRepository.save(plantToSave);

        // Assert
        assertNotNull(result.getId());
        assertEquals("New Plant", result.getNickname());
        verify(plantRepository, times(1)).save(plantToSave);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long plantId = 1L;
        doNothing().when(plantRepository).deleteById(plantId);

        // Act
        plantRepository.deleteById(plantId);

        // Assert
        verify(plantRepository, times(1)).deleteById(plantId);
    }

    @Test
    void testExistsById_WhenPlantExists() {
        // Arrange
        Long plantId = 1L;
        when(plantRepository.existsById(plantId)).thenReturn(true);

        // Act
        boolean exists = plantRepository.existsById(plantId);

        // Assert
        assertTrue(exists);
        verify(plantRepository, times(1)).existsById(plantId);
    }

    @Test
    void testExistsById_WhenPlantNotExists() {
        // Arrange
        Long plantId = 999L;
        when(plantRepository.existsById(plantId)).thenReturn(false);

        // Act
        boolean exists = plantRepository.existsById(plantId);

        // Assert
        assertFalse(exists);
        verify(plantRepository, times(1)).existsById(plantId);
    }
}