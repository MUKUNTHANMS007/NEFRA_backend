package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.entity.Connection;
import com.mukunthan.nefra_connections.entity.Notification;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.enums.ConnectionStatus;
import com.mukunthan.nefra_connections.repository.ConnectionRepository;
import com.mukunthan.nefra_connections.repository.NotificationRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public String sendConnectionRequest(Long senderId, Long recipientId) {
        if (senderId.equals(recipientId)) {
            throw new IllegalArgumentException("You cannot connect with yourself.");
        }

        Optional<Connection> existing = connectionRepository.findConnectionBetweenUsers(senderId, recipientId);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Connection already exists or is pending.");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        Connection connection = new Connection();
        Connection.ConnectionId connectionId = new Connection.ConnectionId(senderId, recipientId);
        connection.setId(connectionId);
        connection.setInvestor(sender);
        connection.setEntrepreneur(recipient);
        connection.setStatus(ConnectionStatus.PENDING);
        connection.setUpdatedAt(LocalDateTime.now());
        connectionRepository.save(connection);

        Notification notification = Notification.builder()
                .user(recipient)
                .message(sender.getFullName() + " has sent you a connection request.")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);

        return "Connection request sent successfully.";
    }

    @Transactional
    public String respondToRequest(Long senderId, Long recipientId, String action) {
        // THE FIX: Use the custom query that checks both directions, ignoring the strict composite key order
        Connection connection = connectionRepository.findConnectionBetweenUsers(senderId, recipientId)
                .orElseThrow(() -> new RuntimeException("Connection request not found."));

        if (!connection.getStatus().equals(ConnectionStatus.PENDING)) {
            throw new IllegalStateException("This request is no longer pending.");
        }

        if ("ACCEPT".equalsIgnoreCase(action)) {
            connection.setStatus(ConnectionStatus.ACCEPTED);
            connection.setUpdatedAt(LocalDateTime.now());
            connectionRepository.save(connection);

            // Determine who to notify based on who initiated the connection
            User originalSender = connection.getInvestor().getId().equals(recipientId) ? connection.getEntrepreneur() : connection.getInvestor();
            User acceptor = connection.getInvestor().getId().equals(recipientId) ? connection.getInvestor() : connection.getEntrepreneur();

            Notification notification = Notification.builder()
                    .user(originalSender)
                    .message(acceptor.getFullName() + " accepted your connection request.")
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);

            return "Connection accepted.";
        } else if ("REJECT".equalsIgnoreCase(action)) {
            connectionRepository.delete(connection);
            return "Connection rejected.";
        }

        throw new IllegalArgumentException("Invalid action. Use ACCEPT or REJECT.");
    }
}