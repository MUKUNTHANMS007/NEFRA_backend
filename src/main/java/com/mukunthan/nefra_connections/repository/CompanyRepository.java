package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // This looks inside the 'user' object in your Company entity for the 'id'
    Optional<Company> findByUser_Id(Long userId);

    List<Company> findByDomainTypeContainingIgnoreCase(String domainType);
}