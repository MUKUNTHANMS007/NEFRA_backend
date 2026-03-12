package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.ProfileResponseDTO;
import com.mukunthan.nefra_connections.entity.Connection;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.enums.ConnectionStatus;
import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import com.mukunthan.nefra_connections.repository.ConnectionRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;

    public ProfileService(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    public ProfileResponseDTO getUserProfile(Long targetUserId, Long viewerId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Node not found in registry"));

        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(targetUser.getId());
        dto.setUsername(targetUser.getUsername());
        dto.setFullName(targetUser.getFullName());
        dto.setLocation(targetUser.getLocation());
        dto.setProfileImageUrl(targetUser.getProfileImageUrl());
        dto.setDescription(targetUser.getDescription());
        dto.setHeadline(targetUser.getHeadline());
        dto.setIndustry(targetUser.getIndustry());
        dto.setFoundedYear(targetUser.getFoundedYear());
        dto.setTotalAssets(targetUser.getTotalAssets());
        dto.setIsVerified(targetUser.getIsVerified());

        // --- NEW: THE MISSING SOCIAL LINKS ---
        dto.setRole(targetUser.getRole());
        dto.setDomainType(targetUser.getDomainType());

        // --- ENUM SAFETY FIX ---
        // Safely converts Enum to String to prevent DTO mapping crashes
        dto.setRole(targetUser.getRole() != null ? UserRole.valueOf(targetUser.getRole().name()) : null);
        dto.setDomainType(targetUser.getDomainType() != null ? DomainType.valueOf(targetUser.getDomainType().name()) : null);

        // Calculate dynamic stats
        dto.setConnectionCount(connectionRepository.countFollowing(targetUserId));
        dto.setPostCount(connectionRepository.countFollowers(targetUserId));

        // CRITICAL UPGRADE: Determine connection status and WHO sent the request
        if (viewerId != null && !viewerId.equals(targetUserId)) {
            Optional<Connection> connection = connectionRepository.findConnectionBetweenUsers(viewerId, targetUserId);

            if (connection.isPresent()) {
                Connection c = connection.get();

                if (c.getStatus() == ConnectionStatus.PENDING) {
                    // Check if the person viewing the profile is the one who sent the request
                    // NOTE: Make sure 'getInvestor()' is the correct field for the sender in your Connection entity!
                    if (c.getInvestor().getId().equals(viewerId)) {
                        dto.setConnectionStatus("PENDING_SENT");
                    } else {
                        dto.setConnectionStatus("PENDING_RECEIVED");
                    }
                } else {
                    dto.setConnectionStatus(c.getStatus().name()); // Will be "ACCEPTED"
                }
            } else {
                dto.setConnectionStatus("NONE");
            }
        } else {
            dto.setConnectionStatus("SELF"); // Viewer is looking at their own profile
        }

        return dto;
    }

    public ProfileResponseDTO updateProfile(Long userId, ProfileResponseDTO updateData) {
        // Your existing update logic here...
        return null;
    }
}