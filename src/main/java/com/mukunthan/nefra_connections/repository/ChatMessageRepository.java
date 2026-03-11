package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // QUERY 1: Fetch Chat History between two specific users
    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.senderId = :u1 AND m.receiverId = :u2) OR " +
            "(m.senderId = :u2 AND m.receiverId = :u1) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(@Param("u1") Long u1, @Param("u2") Long u2);

    // QUERY 2: Fetch unique IDs of people the user has messaged (The "Inbox" logic)
    @Query("SELECT DISTINCT CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END " +
            "FROM ChatMessage m WHERE m.senderId = :userId OR m.receiverId = :userId")
    List<Long> findDistinctConversationPartnerIds(@Param("userId") Long userId);
}