package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Get all unread notifications for a specific user (newest first)
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    // Get all notifications for a specific user (newest first)
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}