package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.*;
import com.mukunthan.nefra_connections.entity.Skill;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.enums.UserRole;
import com.mukunthan.nefra_connections.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final InvestorPortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public ProfileResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> skills = null;
        List<ExperienceDTO> experiences = null;
        List<PortfolioDTO> portfolios = null;

        // Domain Isolation Logic
        if (user.getRole() == UserRole.ENTREPRENEUR) {
            skills = skillRepository.findByUserId(userId).stream()
                    .map(Skill::getSkillName) // Fixed lambda warning
                    .collect(Collectors.toList());

            experiences = experienceRepository.findByUserIdOrderByStartYearDesc(userId).stream()
                    .map(e -> new ExperienceDTO(e.getCompanyName(), e.getCompanyRole(), e.getStartYear(), e.getEndYear()))
                    .collect(Collectors.toList());
        } else if (user.getRole() == UserRole.INVESTOR) {
            portfolios = portfolioRepository.findByInvestorIdOrderByInvestmentYearDesc(userId).stream()
                    .map(p -> new PortfolioDTO(p.getInvestmentYear(), p.getInvestedCompanyName(), p.getInvestmentAmount()))
                    .collect(Collectors.toList());
        }

        return new ProfileResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getDomainType(),
                user.getLocation(),
                user.getDescription(),
                user.getProfileImageUrl(),
                skills,
                experiences,
                portfolios
        );
    }

    @Transactional
    public ProfileResponseDTO updateProfile(Long userId, ProfileResponseDTO updateData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fixed Record Accessor Methods
        if (updateData.fullName() != null) user.setFullName(updateData.fullName());
        if (updateData.location() != null) user.setLocation(updateData.location());
        if (updateData.description() != null) user.setDescription(updateData.description());
        if (updateData.profileImageUrl() != null) user.setProfileImageUrl(updateData.profileImageUrl());

        userRepository.save(user);

        return getUserProfile(userId);
    }
}