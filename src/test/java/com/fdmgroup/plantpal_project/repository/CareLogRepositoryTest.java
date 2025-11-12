package com.fdmgroup.plantpal_project.repository;

import com.fdmgroup.plantpal_project.entity.CareLog;
import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CareLogRepository using Mockito
 */
@ExtendWith(MockitoExtension.class)
class CareLogRepositoryTest {

    @Mock
    private CareLogRepository careLogRepository;

    @Test
    void testFindAll() {
        // Arrange
        CareLog careLog1 = new CareLog();
        careLog1.setId(1L);
        careLog1.setCareType("watered");
        careLog1.setCareDate(LocalDate.now());
        
        CareLog careLog2 = new CareLog();
        careLog2.setId(2L);
        careLog2.setCareType("fertilized");
        careLog2.setCareDate(LocalDate.now().plusDays(1));
        
        List<CareLog> expectedCareLogs = Arrays.asList(careLog1, careLog2);
        when(careLogRepository.findAll()).thenReturn(expectedCareLogs);

        // Act
        List<CareLog> actualCareLogs = careLogRepository.findAll();

        // Assert
        assertEquals(2, actualCareLogs.size());
        assertEquals("watered", actualCareLogs.get(0).getCareType());
        assertEquals("fertilized", actualCareLogs.get(1).getCareType());
        verify(careLogRepository, times(1)).findAll();
    }

    @Test
    void testFindById_WhenCareLogExists() {
        // Arrange
        Long careLogId = 1L;
        CareLog careLog = new CareLog();
        careLog.setId(careLogId);
        careLog.setCareType("watered");
        careLog.setCareDate(LocalDate.now());
        careLog.setNotes("Test notes");
        
        when(careLogRepository.findById(careLogId)).thenReturn(Optional.of(careLog));

        // Act
        Optional<CareLog> foundCareLog = careLogRepository.findById(careLogId);

        // Assert
        assertTrue(foundCareLog.isPresent());
        assertEquals("watered", foundCareLog.get().getCareType());
        assertEquals("Test notes", foundCareLog.get().getNotes());
        verify(careLogRepository, times(1)).findById(careLogId);
    }

    @Test
    void testSaveCareLog() {
        // Arrange
        CareLog careLogToSave = new CareLog();
        careLogToSave.setCareType("watered");
        careLogToSave.setCareDate(LocalDate.now());
        careLogToSave.setNotes("Watering notes");
        
        CareLog savedCareLog = new CareLog();
        savedCareLog.setId(1L);
        savedCareLog.setCareType("watered");
        savedCareLog.setCareDate(LocalDate.now());
        savedCareLog.setNotes("Watering notes");
        
        when(careLogRepository.save(careLogToSave)).thenReturn(savedCareLog);

        // Act
        CareLog result = careLogRepository.save(careLogToSave);

        // Assert
        assertNotNull(result.getId());
        assertEquals("watered", result.getCareType());
        assertEquals("Watering notes", result.getNotes());
        verify(careLogRepository, times(1)).save(careLogToSave);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long careLogId = 1L;
        doNothing().when(careLogRepository).deleteById(careLogId);

        // Act
        careLogRepository.deleteById(careLogId);

        // Assert
        verify(careLogRepository, times(1)).deleteById(careLogId);
    }
}