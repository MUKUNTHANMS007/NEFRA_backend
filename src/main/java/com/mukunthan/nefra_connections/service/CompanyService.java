package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.entity.Company;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.CompanyRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Transactional
    public Company saveOrUpdateCompany(Company incomingData, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return companyRepository.findByUser_Id(userId)
                .map(existing -> {
                    // Check: Does the requester actually own this company?
                    if (!existing.getUser().getId().equals(userId)) {
                        throw new RuntimeException("Unauthorized: Ownership mismatch.");
                    }

                    existing.setName(incomingData.getName());
                    existing.setTagline(incomingData.getTagline());
                    existing.setDescription(incomingData.getDescription());
                    existing.setDomainType(incomingData.getDomainType());
                    existing.setLocation(incomingData.getLocation());
                    existing.setWebsiteUrl(incomingData.getWebsiteUrl());
                    return companyRepository.save(existing);
                })
                .orElseGet(() -> {
                    incomingData.setUser(user);
                    return companyRepository.save(incomingData);
                });
    }
}