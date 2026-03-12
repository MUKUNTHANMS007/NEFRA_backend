package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.Company;
import com.mukunthan.nefra_connections.repository.CompanyRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company data, @RequestParam Long requesterId) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // SECURITY CHECK: Is the person trying to edit actually the owner?
        if (!company.getUser().getId().equals(requesterId)) {
            return ResponseEntity.status(403).body("Access Denied: You do not own this entity.");
        }

        return ResponseEntity.ok(companyService.saveOrUpdateCompany(data, requesterId));
    }
    @GetMapping("/my-company")
    public ResponseEntity<Company> getMyCompany(@RequestParam Long userId) {
        return companyRepository.findByUser_Id(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody Company company, @RequestParam Long userId) {
        return ResponseEntity.ok(companyService.saveOrUpdateCompany(company, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Long id) {
        return companyRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}