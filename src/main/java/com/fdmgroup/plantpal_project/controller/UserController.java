package com.fdmgroup.plantpal_project.controller;

import com.fdmgroup.plantpal_project.entity.User;
import com.fdmgroup.plantpal_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fdmgroup.plantpal_project.dto.LoginRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for handling all User-related HTTP requests.
 * Provides CRUD operations for the User entity.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all Users
     * @return List of all Users with 200 OK status
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Retrieves a specific User by ID
     * @return User entity with 200 OK if found, 404 NOT FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new User
     * @return Created User entity with 201 CREATED status
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (DataIntegrityViolationException e) {
            // This catches database unique constraint violations
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Email or username already exists");
            errorResponse.put("status", HttpStatus.CONFLICT.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    /**
     * Updates an existing User
     * @return Updated User entity with 200 OK if found, 404 NOT FOUND if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                         @Valid @RequestBody User userDetails) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            return ResponseEntity.ok(userService.updateUser(existingUser));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a User by ID
     * @return 200 OK if deleted, 404 NOT FOUND if User doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Login endpoint - authenticates user credentials
     * @return User entity (without password) with 200 OK if valid, 401 UNAUTHORIZED if invalid
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            
            if (user != null) {
                // Return user without password (password is already WRITE_ONLY)
                return ResponseEntity.ok(user);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Login failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
//	  Debugging of User Deletion
//    @GetMapping("/debug/{id}")
//    public ResponseEntity<String> debugUser(@PathVariable Long id) {
//        try {
//            userService.debugUserDeletion(id);
//            return ResponseEntity.ok("Check console for debug information for user ID: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Debug failed: " + e.getMessage());
//        }
//    }
}