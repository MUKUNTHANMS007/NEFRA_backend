package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Follow;
import com.mukunthan.nefra_connections.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    // 1. Check if Mukunthan already follows this specific user
    // This prevents "Duplicate Follow" errors in your service logic
    boolean existsById(FollowId id);

    // 2. Get all users followed by Mukunthan (Your "Following" list)
    List<Follow> findByIdFollowerId(Long followerId);

    // 3. Get everyone following Mukunthan (Your "Followers" list)
    List<Follow> findByIdFollowedId(Long followedId);

    // 4. Counts for the Profile Stats
    long countByIdFollowerId(Long followerId); // "Following" count
    long countByIdFollowedId(Long followedId); // "Followers" count
}