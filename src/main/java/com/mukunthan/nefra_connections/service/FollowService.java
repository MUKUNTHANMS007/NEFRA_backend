package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.entity.Follow;
import com.mukunthan.nefra_connections.entity.FollowId;
import com.mukunthan.nefra_connections.entity.Notification;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.FollowRepository;
import com.mukunthan.nefra_connections.repository.NotificationRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("Cannot follow self.");
        }

        FollowId id = new FollowId(followerId, followedId);
        if (followRepository.existsById(id)) return; // Already following

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        // 1. Save Follow
        followRepository.save(Follow.builder()
                .id(id)
                .follower(follower)
                .followed(followed)
                .build());

        // 2. Update Stats (Using the columns we added to the DB)
        follower.setConnectionCount((follower.getConnectionCount() == null ? 0 : follower.getConnectionCount()) + 1);
        followed.setPostCount((followed.getPostCount() == null ? 0 : followed.getPostCount()) + 1);

        userRepository.save(follower);
        userRepository.save(followed);

        // 3. Create Notification for the person being followed
        notificationRepository.save(Notification.builder()
                .user(followed)
                .message(follower.getFullName() + " started following you!")
                .isRead(false)
                .build());
    }
}