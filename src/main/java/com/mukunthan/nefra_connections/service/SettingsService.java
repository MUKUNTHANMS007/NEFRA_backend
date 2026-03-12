package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.SettingsUpdateDTO;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.entity.UserSettings;
import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Transactional
    public void updateSettings(Long userId, SettingsUpdateDTO dto) {
        // 1. Update Core User Data
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Node not found in registry"));

        if (dto.fullName() != null) user.setFullName(dto.fullName());
        if (dto.headline() != null) user.setHeadline(dto.headline());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.industry() != null) user.setIndustry(dto.industry());
        if (dto.location() != null) user.setLocation(dto.location());
        if (dto.description() != null) user.setDescription(dto.description());
        if (dto.linkedinUrl() != null) user.setLinkedinUrl(dto.linkedinUrl());
        if (dto.githubUrl() != null) user.setGithubUrl(dto.githubUrl());
        if (dto.totalAssets() != null) user.setTotalAssets(dto.totalAssets());
        if (dto.foundedYear() != null) user.setFoundedYear(dto.foundedYear());

        // Safely parse Enums
        if (dto.role() != null) {
            try { user.setRole(UserRole.valueOf(dto.role().toUpperCase())); } catch (Exception ignored) {}
        }
        if (dto.domainType() != null) {
            try { user.setDomainType(DomainType.valueOf(dto.domainType().toUpperCase())); } catch (Exception ignored) {}
        }

        userRepository.save(user);

        // 2. Update Toggle Settings
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserSettings newSettings = new UserSettings();
                    newSettings.setUser(user);
                    return newSettings;
                });

        if (dto.profileVisibility() != null) settings.setProfileVisibility(dto.profileVisibility());
        if (dto.connectionRequests() != null) settings.setConnectionRequests(dto.connectionRequests());
        if (dto.searchVisibility() != null) settings.setSearchVisibility(dto.searchVisibility());
        if (dto.activityStatus() != null) settings.setActivityStatus(dto.activityStatus());
        if (dto.emailNotifications() != null) settings.setEmailNotifications(dto.emailNotifications());
        if (dto.connectionUpdates() != null) settings.setConnectionUpdates(dto.connectionUpdates());
        if (dto.messageAlerts() != null) settings.setMessageAlerts(dto.messageAlerts());
        if (dto.weeklyDigest() != null) settings.setWeeklyDigest(dto.weeklyDigest());
        if (dto.twoFactor() != null) settings.setTwoFactor(dto.twoFactor());
        if (dto.loginAlerts() != null) settings.setLoginAlerts(dto.loginAlerts());
        if (dto.marketingEmails() != null) settings.setMarketingEmails(dto.marketingEmails());
        if (dto.productUpdates() != null) settings.setProductUpdates(dto.productUpdates());
        if (dto.eventInvitations() != null) settings.setEventInvitations(dto.eventInvitations());

        userSettingsRepository.save(settings);
    }

    public SettingsUpdateDTO getSettings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Node not found"));

        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings()); // Default settings if none exist

        return new SettingsUpdateDTO(
                user.getFullName(),
                user.getHeadline(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getDomainType() != null ? user.getDomainType().name() : null,
                user.getIndustry(),
                null, // company is not in User entity currently based on your schema
                user.getLocation(),
                user.getDescription(),
                user.getLinkedinUrl(),
                user.getGithubUrl(),
                user.getTotalAssets(),
                user.getFoundedYear(),
                settings.isProfileVisibility(),
                settings.isConnectionRequests(),
                settings.isSearchVisibility(),
                settings.isActivityStatus(),
                settings.isEmailNotifications(),
                settings.isConnectionUpdates(),
                settings.isMessageAlerts(),
                settings.isWeeklyDigest(),
                settings.isTwoFactor(),
                settings.isLoginAlerts(),
                settings.isMarketingEmails(),
                settings.isProductUpdates(),
                settings.isEventInvitations()
        );
    }
}