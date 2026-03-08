package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.Company;
import com.mukunthan.nefra_connections.repository.CompanyRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    // FOR INVESTORS: Get all companies (with optional domain filter)
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam(required = false) String domain) {
        if (domain != null && !domain.isEmpty()) {
            return ResponseEntity.ok(companyRepository.findByDomainTypeContainingIgnoreCase(domain));
        }
        return ResponseEntity.ok(companyRepository.findAll());
    }

    // FOR INVESTORS: Get a specific company profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // THE MISSING LINK: Save or Update a company profile
    @PostMapping
    public ResponseEntity<Company> saveOrUpdateCompany(@RequestBody Company company, @RequestParam Long entrepreneurId) {
        // 1. Link the company to the existing user
        return userRepository.findById(entrepreneurId).map(user -> {
            company.setEntrepreneur(user);

            // 2. Check if the user already has a company (Update instead of Create)
            return companyRepository.findByEntrepreneurId(entrepreneurId)
                    .map(existingCompany -> {
                        company.setId(existingCompany.getId()); // Keep the same ID for update
                        return ResponseEntity.ok(companyRepository.save(company));
                    })
                    .orElseGet(() -> ResponseEntity.ok(companyRepository.save(company)));
        }).orElse(ResponseEntity.badRequest().build());
    }

    // FOR ENTREPRENEURS: Get their own company
    @GetMapping("/my-company")
    public ResponseEntity<Company> getMyCompany(@RequestParam Long userId) {
        return companyRepository.findByEntrepreneurId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build()); // Returns 204 if they haven't created one yet
    }
}