package com.mukunthan.nefra_connections.dto;

public record SettingsUpdateDTO(
        // Account Info
        String fullName,
        String email,
        String role,
        String company,

        // Privacy & Notifications Toggles
        boolean profileVisibility,
        boolean connectionRequests,
        boolean searchVisibility,
        boolean activityStatus,
        boolean emailNotifications,
        boolean connectionUpdates,
        boolean messageAlerts,
        boolean weeklyDigest,
        boolean twoFactor,
        boolean loginAlerts,
        boolean marketingEmails,
        boolean productUpdates,
        boolean eventInvitations
) {}