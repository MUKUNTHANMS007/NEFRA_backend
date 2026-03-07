package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // New Search Engine Queries
    List<User> findByRole(UserRole role);
    List<User> findByRoleAndDomainType(UserRole role, DomainType domainType);
}