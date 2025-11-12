package com.fdmgroup.plantpal_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.plantpal_project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}