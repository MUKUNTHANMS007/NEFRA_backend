package com.mukunthan.nefra_connections.service;
import com.mukunthan.nefra_connections.enums.UserRole; // Or wherever your enum is located
import com.mukunthan.nefra_connections.dto.SettingsUpdateDTO;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.entity.UserSettings;
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
    public void updateSettings(Long userId, SettingsUpdateDTO request) {
        // 1. Find the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Update basic User info
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        if (request.role() != null && !request.role().isBlank()) {
            user.setRole(UserRole.valueOf(request.role().toUpperCase()));
        }
        userRepository.save(user);

        // 3. Find or Create UserSettings
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings());

        if (settings.getId() == null) {
            settings.setUser(user);
        }

        // 4. Update the 13 toggles
        settings.setProfileVisibility(request.profileVisibility());
        settings.setConnectionRequests(request.connectionRequests());
        settings.setSearchVisibility(request.searchVisibility());
        settings.setActivityStatus(request.activityStatus());
        settings.setEmailNotifications(request.emailNotifications());
        settings.setConnectionUpdates(request.connectionUpdates());
        settings.setMessageAlerts(request.messageAlerts());
        settings.setWeeklyDigest(request.weeklyDigest());
        settings.setTwoFactor(request.twoFactor());
        settings.setLoginAlerts(request.loginAlerts());
        settings.setMarketingEmails(request.marketingEmails());
        settings.setProductUpdates(request.productUpdates());
        settings.setEventInvitations(request.eventInvitations());

        // 5. Save to Database
        userSettingsRepository.save(settings);
    }

    @Transactional(readOnly = true)
    public SettingsUpdateDTO getSettings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If settings don't exist yet, it creates an empty shell with your default TRUE/FALSE values
        UserSettings settings = userSettingsRepository.findByUserId(userId)
                .orElse(new UserSettings());

        return new SettingsUpdateDTO(
                user.getFullName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : "ENTREPRENEUR",
                "", // Connecting the company object is a future step
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