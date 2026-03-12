package com.mukunthan.nefra_connections.dto;

import java.math.BigDecimal;

public record SettingsUpdateDTO(
        // Core User Identity Fields
        String fullName,
        String headline,
        String email,
        String role,
        String domainType,
        String industry,
        String company,
        String location,
        String description,
        String linkedinUrl,
        String githubUrl,
        BigDecimal totalAssets,
        Integer foundedYear,

        // Privacy & Notifications Toggles (UserSettings Table)
        Boolean profileVisibility,
        Boolean connectionRequests,
        Boolean searchVisibility,
        Boolean activityStatus,
        Boolean emailNotifications,
        Boolean connectionUpdates,
        Boolean messageAlerts,
        Boolean weeklyDigest,
        Boolean twoFactor,
        Boolean loginAlerts,
        Boolean marketingEmails,
        Boolean productUpdates,
        Boolean eventInvitations
) {}