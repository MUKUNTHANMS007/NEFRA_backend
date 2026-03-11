package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.ChatMessage;
import com.mukunthan.nefra_connections.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

@Controller
public class ChatWSController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatRepository;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // 1. Set timestamp and save to PostgreSQL
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage savedMsg = chatRepository.save(chatMessage);

        // 2. THE NUCLEAR OPTION: Direct String Paths
        // We send to /user/ID/queue/messages.
        // This is the EXACT string the frontend listens to.

        // Relay to Receiver
        messagingTemplate.convertAndSend("/user/" + savedMsg.getReceiverId() + "/queue/messages", savedMsg);

        // Relay back to Sender (This replaces the need for a manual refresh!)
        messagingTemplate.convertAndSend("/user/" + savedMsg.getSenderId() + "/queue/messages", savedMsg);
    }
}