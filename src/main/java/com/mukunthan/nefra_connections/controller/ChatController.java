package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.ChatMessage;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.ChatMessageRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * GET INBOX / CONVERSATIONS
     * This returns ONLY the users you have actually chatted with.
     */
    @GetMapping("/conversations")
    public ResponseEntity<List<User>> getConversations(
            @RequestHeader("X-User-Id") Long currentUserId
    ) {
        // 1. Get the IDs of everyone this user has a message history with
        List<Long> partnerIds = chatMessageRepository.findDistinctConversationPartnerIds(currentUserId);

        // 2. Fetch the User details for those IDs from the database
        List<User> conversations = userRepository.findAllById(partnerIds);

        return ResponseEntity.ok(conversations);
    }

    /**
     * GET CHAT HISTORY
     */
    @GetMapping("/history/{otherUserId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @PathVariable Long otherUserId,
            @RequestHeader("X-User-Id") Long currentUserId
    ) {
        List<ChatMessage> history = chatMessageRepository.findChatHistory(currentUserId, otherUserId);
        return ResponseEntity.ok(history);
    }

    /**
     * MARK AS READ
     */
    @PutMapping("/read/{senderId}")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long senderId,
            @RequestHeader("X-User-Id") Long receiverId
    ) {
        // Future: chatMessageRepository.markMessagesAsRead(senderId, receiverId);
        return ResponseEntity.ok().build();
    }
}