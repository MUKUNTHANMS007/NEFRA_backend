package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_settings")
@Data
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private boolean profileVisibility = true;
    private boolean connectionRequests = true;
    private boolean searchVisibility = true;
    private boolean activityStatus = true;
    private boolean emailNotifications = true;
    private boolean connectionUpdates = true;
    private boolean messageAlerts = true;
    private boolean weeklyDigest = false;
    private boolean twoFactor = false;
    private boolean loginAlerts = true;
    private boolean marketingEmails = false;
    private boolean productUpdates = true;
    private boolean eventInvitations = true;
}