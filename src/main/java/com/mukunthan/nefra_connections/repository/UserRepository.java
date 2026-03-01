package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * The Standard Auth Method.
     * Returning Optional allows us to use .map() and .isPresent() in AuthController.
     */
    Optional<User> findByEmail(String email);

    /**
     * Prevent duplicate accounts during Signup.
     */
    boolean existsByEmail(String email);

    /**
     * Filter users by the Role Enum (ENTREPRENEUR or INVESTOR).
     */
    List<User> findByRole(User.Role role);

    /**
     * Filter users by industry (Case-insensitive).
     */
    List<User> findByIndustryIgnoreCase(String industry);

    /**
     * Search users by name (Partial match, case-insensitive).
     */
    List<User> findByNameContainingIgnoreCase(String name);
}