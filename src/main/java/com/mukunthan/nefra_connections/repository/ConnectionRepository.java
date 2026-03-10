package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Connection;
import com.mukunthan.nefra_connections.enums.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Connection.ConnectionId> {

    List<Connection> findByInvestorId(Long investorId);
    List<Connection> findByEntrepreneurId(Long entrepreneurId);

    // 1. Count connections where the user is the Investor (Following)
    @Query("SELECT COUNT(c) FROM Connection c WHERE c.investor.id = :userId AND c.status = 'ACCEPTED'")
    Long countFollowing(@Param("userId") Long userId);

    // 2. Count connections where the user is the Entrepreneur (Followers)
    @Query("SELECT COUNT(c) FROM Connection c WHERE c.entrepreneur.id = :userId AND c.status = 'ACCEPTED'")
    Long countFollowers(@Param("userId") Long userId);

    // 3. Find the specific connection state between two users (order independent)
    @Query("SELECT c FROM Connection c WHERE " +
            "(c.investor.id = :userA AND c.entrepreneur.id = :userB) OR " +
            "(c.investor.id = :userB AND c.entrepreneur.id = :userA)")
    Optional<Connection> findConnectionBetweenUsers(@Param("userA") Long userA, @Param("userB") Long userB);
}