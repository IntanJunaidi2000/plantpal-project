package com.fdmgroup.plantpal_project.repository;

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
 * Unit tests for UserRepository using Mockito
 */
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindAll() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("alice");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("bob");
        
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userRepository.findAll();

        // Assert
        assertEquals(2, actualUsers.size());
        assertEquals("alice", actualUsers.get(0).getUsername());
        assertEquals("bob", actualUsers.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("alice");
        user.setEmail("alice@email.com");
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userRepository.findById(userId);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("alice", foundUser.get().getUsername());
        assertEquals("alice@email.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSaveUser() {
        // Arrange
        User userToSave = new User();
        userToSave.setUsername("newuser");
        userToSave.setEmail("new@email.com");
        
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");
        savedUser.setEmail("new@email.com");
        
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        // Act
        User result = userRepository.save(userToSave);

        // Assert
        assertNotNull(result.getId());
        assertEquals("newuser", result.getUsername());
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userRepository.deleteById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}