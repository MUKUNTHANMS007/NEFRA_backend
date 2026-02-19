package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * By extending JpaRepository, we get standard CRUD operations (save, delete, findById)
 * and pagination support without writing any SQL.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their name.
     * Spring Data JPA automatically generates the SQL for this based on the method name.
     */
    User findByName(String name);

    /**
     * Finds a user by their email (useful if you add an email field later).
     */
    User findByEmail(String email);

    /**
     * Checks if a user exists with a specific name.
     */
    boolean existsByName(String name);
}