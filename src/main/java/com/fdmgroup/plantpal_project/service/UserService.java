package com.fdmgroup.plantpal_project.service;

import com.fdmgroup.plantpal_project.entity.User;
import com.fdmgroup.plantpal_project.exception.EmailAlreadyExistsException;
import com.fdmgroup.plantpal_project.exception.ResourceNotFoundException;
import com.fdmgroup.plantpal_project.exception.UsernameAlreadyExistsException;
import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.repository.UserRepository;
import com.fdmgroup.plantpal_project.repository.PlantRepository;
import com.fdmgroup.plantpal_project.repository.CareLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for User entity business logic.
 * Handles all CRUD operations and acts as middleware between Controller and Repository.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private CareLogRepository careLogRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
    	// Email uniqueness check
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        
        // Username uniqueness check  
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long id) {
        // Check if user exists
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        try {
            // Step 1: Delete care logs for all plants of this user
            String deleteCareLogsSQL = 
                "DELETE cl FROM care_logs cl " +
                "INNER JOIN plants p ON cl.plant_id = p.id " +
                "WHERE p.user_id = ?";
            jdbcTemplate.update(deleteCareLogsSQL, id);

            // Step 2: Delete all plants for this user
            String deletePlantsSQL = "DELETE FROM plants WHERE user_id = ?";
            jdbcTemplate.update(deletePlantsSQL, id);

            // Step 3: Delete the user
            String deleteUserSQL = "DELETE FROM users WHERE id = ?";
            jdbcTemplate.update(deleteUserSQL, id);

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }
    
    public User authenticateUser(String email, String password) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    
//    @Transactional(readOnly = true)
//    public void debugUserDeletion(Long id) {
//        System.out.println("=== DEBUG USER DELETION ===");
//        
//        // Check if user exists
//        boolean userExists = userRepository.existsById(id);
//        System.out.println("1. User exists: " + userExists);
//        
//        if (userExists) {
//            // Check plants for this user
//            Integer plantCount = jdbcTemplate.queryForObject(
//                "SELECT COUNT(*) FROM plants WHERE user_id = ?", Integer.class, id);
//            System.out.println("2. Plants for user: " + plantCount);
//            
//            // Check care logs for this user's plants
//            Integer careLogCount = jdbcTemplate.queryForObject(
//                "SELECT COUNT(*) FROM care_logs WHERE plant_id IN (SELECT id FROM plants WHERE user_id = ?)", 
//                Integer.class, id);
//            System.out.println("3. Care logs for user's plants: " + careLogCount);
//            
//            // List specific plant IDs
//            List<Long> plantIds = jdbcTemplate.queryForList(
//                "SELECT id FROM plants WHERE user_id = ?", Long.class, id);
//            System.out.println("4. Plant IDs: " + plantIds);
//            
//            // List specific care log IDs
//            if (!plantIds.isEmpty()) {
//                List<Long> careLogIds = jdbcTemplate.queryForList(
//                    "SELECT id FROM care_logs WHERE plant_id IN (" + 
//                    plantIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")", 
//                    Long.class);
//                System.out.println("5. Care log IDs: " + careLogIds);
//            }
//        }
//        System.out.println("=== DEBUG COMPLETE ===");
//    }
}